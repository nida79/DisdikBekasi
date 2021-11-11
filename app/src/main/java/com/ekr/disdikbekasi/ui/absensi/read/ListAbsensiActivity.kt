package com.ekr.disdikbekasi.ui.absensi.read

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekr.disdikbekasi.databinding.ActivityListAbsensiBinding
import com.ekr.disdikbekasi.model.absensi.GetDataAbsensi
import com.ekr.disdikbekasi.model.absensi.ResponseGetAbsensi
import com.ekr.disdikbekasi.ui.MainActivity
import com.ekr.disdikbekasi.ui.absensi.create.FormAbsensiActivity
import com.ekr.disdikbekasi.utils.DateHelper
import com.ekr.disdikbekasi.utils.DialogHelper
import com.ekr.disdikbekasi.utils.SessionManager
import kotlinx.android.synthetic.main.dialog_filter.*
import java.text.DateFormat
import java.util.*

class ListAbsensiActivity : AppCompatActivity(), GetAbsensiContract.View {
    private lateinit var _binding: ActivityListAbsensiBinding
    private lateinit var adapterAbsensi: AbsensiAdapter
    private lateinit var sessionManager: SessionManager
    private lateinit var presenter: GetAbsensiPresenter
    private lateinit var data: List<GetDataAbsensi>
    private lateinit var dialog: Dialog
    private lateinit var dialogFilter: Dialog
    private var startDate = ""
    private var endDate = ""
    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListAbsensiBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)
        sessionManager = SessionManager(this)
        dialog = DialogHelper.globalLoading(this)
        dialogFilter = DialogHelper.dialogFilter(this)
        presenter = GetAbsensiPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.doGetAttendance(
            sessionManager.prefToken,
            sessionManager.prefID.toInt(),
            startDate,
            endDate
        )
    }

    @SuppressLint("SimpleDateFormat")
    override fun initListener() {
        adapterAbsensi = AbsensiAdapter(arrayListOf())

        val currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.time)
        _binding.tvDateAbsensi.text = currentDate.toString()
        _binding.rvAbsensi.apply {
            adapter = adapterAbsensi
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
        }
        _binding.swpAbsensi.setOnRefreshListener {
            _binding.swpAbsensi.isRefreshing = false
            presenter.doGetAttendance(
                sessionManager.prefToken,
                sessionManager.prefID.toInt(),
                startDate,
                endDate
            )
        }
        adapterAbsensi.setOnItemClickListener(object : AbsensiAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, data: GetDataAbsensi) {
                val intent = Intent(applicationContext, DetailAbsensiActivity::class.java)
                intent.putExtra("data", data)
                startActivity(intent)
            }

        })
        _binding.btnFilter.setOnClickListener {
            dialogFilter.show()
            dialogFilter.tanggal_awal.showSoftInputOnFocus = false
            dialogFilter.tanggal_akhir.showSoftInputOnFocus = false

            dialogFilter.tanggal_awal.setOnClickListener {
                DateHelper.chooseDate(this,dialogFilter.tanggal_awal)

            }
            dialogFilter.tanggal_akhir.setOnClickListener {
                DateHelper.chooseDate(this,dialogFilter.tanggal_akhir)
            }

            dialogFilter.btn_submit_filter.setOnClickListener {
                startDate = dialogFilter.tanggal_awal.text.toString()
                endDate = dialogFilter.tanggal_akhir.text.toString()
                when {
                    startDate.isEmpty() -> showMessage("Tanggal Awal Tidak Boleh Kosong")
                    endDate.isEmpty() -> showMessage("Tanggal Akhir Tidak Boleh Kosong")
                    else -> {
                        dialogFilter.dismiss()
                        presenter.doGetAttendance(
                            sessionManager.prefToken,
                            sessionManager.prefID.toInt(),
                            startDate,
                            endDate
                        )
                    }
                }
            }
            dialogFilter.pop_close_laporan.setOnClickListener { dialogFilter.dismiss() }

        }
        _binding.floatingAddAbsensi.setOnClickListener {
            startActivity(Intent(this,FormAbsensiActivity::class.java))
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

    override fun resultGetdata(responseGetAbsensi: ResponseGetAbsensi) {
        data = responseGetAbsensi.data!!
        data.let { adapterAbsensi.setData(data) }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
        finish()
    }


}