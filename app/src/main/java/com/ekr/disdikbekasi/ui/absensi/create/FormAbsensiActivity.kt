package com.ekr.disdikbekasi.ui.absensi.create

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.*
import com.ekr.disdikbekasi.databinding.ActivityFormAbsensiBinding
import com.ekr.disdikbekasi.model.ResponseGlobal
import com.ekr.disdikbekasi.model.absensi.PostAbsensiIn
import com.ekr.disdikbekasi.model.absensi.PostAbsensiOut
import com.ekr.disdikbekasi.utils.DialogHelper
import com.ekr.disdikbekasi.utils.SessionManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FormAbsensiActivity : AppCompatActivity(), PostAbsenContract.View {
    private lateinit var binding: ActivityFormAbsensiBinding
    private lateinit var presenter: PostAbsenPresenter
    private lateinit var dialog: Dialog
    private lateinit var qrScanner: CodeScanner
    private lateinit var sessionManager: SessionManager
    private lateinit var searchLocation: FusedLocationProviderClient
    private lateinit var dataAbsensiIn: PostAbsensiIn
    private lateinit var dataAbsensiOut: PostAbsensiOut
    private var latitude = ""
    private var longitude = ""
    private var statusQr = arrayOf<String>()

    @SuppressLint("VisibleForTests")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormAbsensiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        qrScanner = CodeScanner(this, binding.scn)
        dialog = DialogHelper.globalLoading(this)
        presenter = PostAbsenPresenter(this)
        sessionManager = SessionManager(this)

    }


    @SuppressLint("VisibleForTests")
    override fun initListener() {

        searchLocation = FusedLocationProviderClient(this)
        requestPersmission()
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        binding.resultDateScan.text = date.toString()
        binding.resultTimeScan.text = time.toString()
        Log.e("AbsensiForm", "date: $date - time : $time")
        binding.btnAttendanceSubmit.setOnClickListener {

            when {
                latitude == "" -> {
                    showMessage("Sedang Melacak Alamat Mohon Coba Kembali")
                    getAlamat()
                }
                longitude == "" -> {
                    showMessage("Sedang Melacak Alamat Mohon Coba Kembali")
                    getAlamat()
                }
                statusQr[2] == "masuk" -> {
                    dataAbsensiIn = PostAbsensiIn(
                        date,
                        time,
                        binding.spinnerAttendance.selectedItem.toString(),
                        latitude,
                        longitude
                    )
                    presenter.doAttendanceIn(sessionManager.prefToken, dataAbsensiIn)
                }
                statusQr[2] == "keluar" -> {
                    dataAbsensiOut = PostAbsensiOut(date, time)
                    presenter.doAttendanceOut(sessionManager.prefToken, dataAbsensiOut)
                }
            }
        }

    }


    private fun codeScanner() {
        qrScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                     statusQr = it.toString().split("-").toTypedArray()
                    presenter.doValidateQR(sessionManager.prefToken, it.toString())

                }

            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "qrScanner: ${it.message}")
                }
            }

            binding.scn.setOnClickListener {
                qrScanner.startPreview()
            }

        }
    }

    override fun onLoading(loading: Boolean) {
        when (loading) {
            true -> dialog.show()
            false -> dialog.dismiss()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResultValidate(responseGlobal: ResponseGlobal) {
        showMessage(responseGlobal.message)
        if (responseGlobal.status) {
            binding.resultStatusScan.text = statusQr[2].replaceFirstChar { it.uppercase() }
            binding.tvSuccesScan.visibility = View.VISIBLE
            binding.llResultScan.visibility = View.VISIBLE
        }
    }

    override fun onResult(responseGlobal: ResponseGlobal) {
        showMessage(responseGlobal.message)
        if (responseGlobal.status) {
            finishAffinity()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        qrScanner.startPreview()
    }

    override fun onPause() {
        qrScanner.releaseResources()
        super.onPause()
    }

    private fun requestPersmission() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        getAlamat()
                        codeScanner()
                    }

                    if (report.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(
                    applicationContext, "Error occurred! ", Toast.LENGTH_LONG
                ).show()
            }
            .onSameThread()
            .check()
    }

    @SuppressLint("MissingPermission")
    private fun getAlamat() {
        // Permissions ok, we get last location
        searchLocation.lastLocation.addOnCompleteListener { task: Task<Location?> ->
            val location = task.result
            if (location != null) {
                val geocoder = Geocoder(applicationContext, Locale.getDefault())
                try {
                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()
                    val addresses =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    Log.e("Hasil", "getLong: $longitude")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Perizinan Diperlukan")
        builder.setMessage("Aktifkan Perizinan untuk Melakukan Absensi")
        builder.setPositiveButton("BUKA PENGATURAN") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
            openSetting()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
        builder.show()
    }

    private fun openSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }
}