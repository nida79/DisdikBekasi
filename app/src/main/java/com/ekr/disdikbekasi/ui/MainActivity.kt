package com.ekr.disdikbekasi.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ekr.disdikbekasi.databinding.ActivityMainBinding
import com.ekr.disdikbekasi.ui.absensi.create.FormAbsensiActivity
import com.ekr.disdikbekasi.ui.absensi.read.ListAbsensiActivity
import com.ekr.disdikbekasi.ui.cuti.FormCutiActivity
import com.ekr.disdikbekasi.ui.izin.IzinActivity
import com.ekr.disdikbekasi.ui.login.LoginActivity
import com.ekr.disdikbekasi.ui.sakit.FormSakitActivity
import com.ekr.disdikbekasi.utils.GlideHelper
import com.ekr.disdikbekasi.utils.SessionManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchLocation: FusedLocationProviderClient

    @SuppressLint("VisibleForTests")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sessionManager = SessionManager(this)
        searchLocation = FusedLocationProviderClient(this)
        binding.tvNameMain.text = sessionManager.prefFullname
        if (sessionManager.prefFoto!=""){
            GlideHelper.setImage(applicationContext, sessionManager.prefFoto, binding.civMain)
        }

        binding.iconAbsensi.setOnClickListener {
            startActivity(Intent(applicationContext, ListAbsensiActivity::class.java))

        }
        binding.iconPengajuan.setOnClickListener {
            startActivity(Intent(applicationContext, FormCutiActivity::class.java))

        }
        binding.iconSakit.setOnClickListener {
            startActivity(Intent(applicationContext, FormSakitActivity::class.java))

        }
        binding.btnLogot.setOnClickListener {
            sessionManager.logOut()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finishAffinity()
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        requestLocationPersmission()
    }

    override fun onResume() {
        super.onResume()
        requestLocationPersmission()
    }

    private fun requestLocationPersmission() {
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
                    applicationContext, it.name, Toast.LENGTH_LONG
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
                    val addresses =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    Log.e("Hasil", "getAlamat: $addresses")
                    tv_alamat_main.text = addresses[0].getAddressLine(0)
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