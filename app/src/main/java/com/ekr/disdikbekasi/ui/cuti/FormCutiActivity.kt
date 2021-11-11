package com.ekr.disdikbekasi.ui.cuti

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ekr.disdikbekasi.databinding.ActivityFormCutiBinding
import com.ekr.disdikbekasi.model.cuti.ResponseCuti
import com.ekr.disdikbekasi.utils.DateHelper
import com.ekr.disdikbekasi.utils.DialogHelper
import com.ekr.disdikbekasi.utils.SessionManager

class FormCutiActivity : AppCompatActivity(), FormCutiContract.View {
    private lateinit var presenter: FormCutiPresenter
    private lateinit var _binding: ActivityFormCutiBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFormCutiBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        dialog = DialogHelper.globalLoading(this)
        sessionManager = SessionManager(this)
        presenter = FormCutiPresenter(this)

    }

    override fun initListener() {
        _binding.namaCuti.text = sessionManager.prefFullname
        _binding.nipCuti.text = sessionManager.prefNIP
        _binding.sisaCuti.text = sessionManager.prefCuti
        _binding.inputTanggalAwalCuti.showSoftInputOnFocus = false
        _binding.inputTanggalAkhirCuti.showSoftInputOnFocus = false
        _binding.inputTanggalAwalCuti.setOnClickListener {
            DateHelper.chooseDate(this,_binding.inputTanggalAwalCuti)
        }
        _binding.inputTanggalAkhirCuti . setOnClickListener {
            DateHelper.chooseDate(this, _binding.inputTanggalAkhirCuti)
        }
        _binding.btnKirimCuti.setOnClickListener {
            when {
                _binding.inputTanggalAwalCuti.text.toString().isEmpty() -> {
                    showMessage("Tanggal awal Tidak Boleh Kosong!!")
                }
                _binding.inputTanggalAkhirCuti.text.toString().isEmpty() -> {
                    showMessage("Tanggal akhir Tidak Boleh Kosong!!")
                }
                _binding.inputKeteranganCuti.text.toString().isEmpty() -> {
                    _binding.inputKeteranganCuti.requestFocus()
                    _binding.inputKeteranganCuti.error = "Kolom Tidak Boleh Kosong"
                }
                else -> presenter.doAnnualLeave(
                        sessionManager.prefToken,
                        _binding.inputTanggalAwalCuti.text.toString(),
                        _binding.inputTanggalAkhirCuti.text.toString(),
                        _binding.inputKeteranganCuti.text.toString()
                )
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

    override fun onResult(responseCuti: ResponseCuti) {
        if (responseCuti.status) {
            sessionManager.prefCuti = responseCuti.data.sisaCuti.toString()
            finish()
        }
    }
}