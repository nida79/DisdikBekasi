package com.ekr.disdikbekasi.ui.sakit

import android.Manifest
import android.R.attr
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ekr.disdikbekasi.databinding.ActivityFormSakitBinding
import com.ekr.disdikbekasi.model.ResponseGlobal
import com.ekr.disdikbekasi.model.upload.ResponseUpload
import com.ekr.disdikbekasi.utils.DateHelper
import com.ekr.disdikbekasi.utils.DialogHelper
import com.ekr.disdikbekasi.utils.SessionManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File


class FormSakitActivity : AppCompatActivity(),FormSakitContract.View {
    private lateinit var presenter: FormSakitPresenter
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityFormSakitBinding
    private lateinit var dialog : Dialog
    private  var photo_id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormSakitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = DialogHelper.globalLoading(this)
        sessionManager = SessionManager(this)
        presenter = FormSakitPresenter(this)
    }

    override fun initListener() {
        binding.namaIzin.text = sessionManager.prefFullname
        binding.nipIzin.text = sessionManager.prefNIP
        binding.inputTanggalAwalIzin.showSoftInputOnFocus = false
        binding.inputTanggalAkhirIzin.showSoftInputOnFocus = false
        binding.inputTanggalAwalIzin.setOnClickListener {
            DateHelper.chooseDate(this,binding.inputTanggalAwalIzin)
        }
        binding.inputTanggalAkhirIzin . setOnClickListener {
            DateHelper.chooseDate(this, binding.inputTanggalAkhirIzin)
        }
        binding.btnUploadIzin.setOnClickListener {
            uploadPhoto()
        }

        binding.btnKirimIzin.setOnClickListener {
            when {
                binding.inputTanggalAwalIzin.text.toString().isEmpty() -> {
                    showMessage("Tanggal awal Tidak Boleh Kosong!!")
                }
                binding.inputTanggalAkhirIzin.text.toString().isEmpty() -> {
                    showMessage("Tanggal akhir Tidak Boleh Kosong!!")
                }
                binding.inputKeteranganIzin.text.toString().isEmpty() -> {
                    binding.inputKeteranganIzin.requestFocus()
                    binding.inputKeteranganIzin.error = "Kolom Tidak Boleh Kosong"
                }
                else -> presenter.doPostOtherLeave(
                    sessionManager.prefToken,
                    binding.inputTanggalAwalIzin.text.toString(),
                    binding.inputTanggalAkhirIzin.text.toString(),
                    binding.inputKeteranganIzin.text.toString(),
                    photo_id
                )
            }
        }
    }

    override fun onLoading(loading: Boolean) {
       when(loading){
           true->dialog.show()
           false->dialog.dismiss()
       }
    }

    override fun showMessage(message: String) {
       Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }

    override fun resultUpload(responseUpload: ResponseUpload) {
        if (responseUpload.status){
            photo_id = responseUpload.dataUpload.id!!.toInt()
            showMessage(responseUpload.message)
            binding.imgSakit.visibility = View.VISIBLE
            binding.btnKirimIzin.visibility = View.VISIBLE
        }
    }

    override fun resultOtherLeave(responseGlobal: ResponseGlobal) {
        if (responseGlobal.status){
            showMessage(responseGlobal.message)
            finish()
        }
    }

    private fun uploadPhoto() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        ImagePicker.with(this@FormSakitActivity)
                            .crop()
                            .compress(1024)
                            .maxResultSize(1080, 1080)
                            .start()
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

    private fun openSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Perizinan Diperlukan")
        builder.setMessage("Aktifkan Perizinan untuk Mengupload Gambar")
        builder.setPositiveButton("BUKA PENGATURAN") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
            openSetting()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val fileUri = data?.data
                binding.imgSakit.setImageURI(fileUri)
                val file: File = ImagePicker.getFile(data)!!
                val filename  = file.name.toString()
                val type = filename.substring(filename.lastIndexOf("."))

                presenter.doUploadPhoto(sessionManager.prefToken, file,filename,type)

            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Batal Mengambil Gambar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}