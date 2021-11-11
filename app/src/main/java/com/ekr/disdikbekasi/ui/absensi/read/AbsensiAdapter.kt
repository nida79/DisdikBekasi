package com.ekr.disdikbekasi.ui.absensi.read

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekr.disdikbekasi.R
import com.ekr.disdikbekasi.model.absensi.GetDataAbsensi
import kotlinx.android.synthetic.main.item_rv.view.*
import java.util.*

class AbsensiAdapter(private var dataAbsensi: ArrayList<GetDataAbsensi>) :
    RecyclerView.Adapter<AbsensiAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int, data: GetDataAbsensi)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOnItemClickListener(listener: OnItemClickListener) {
        notifyDataSetChanged()
        mListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<GetDataAbsensi>) {
        dataAbsensi.clear()
        dataAbsensi.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataAbsensi[position])
    }

    override fun getItemCount(): Int {
        return dataAbsensi.size
    }

    class ViewHolder(itemView: View, private val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(data: GetDataAbsensi) {
            with(itemView) {
                val position = adapterPosition
                tv_rv_tgl.text = data.date
                tv_rv_in.text = data.inX
                tv_rv_out.text = data.out
                tv_rv_ket.text = data.note

                setOnClickListener {
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position, data)
                    }
                }
            }
        }

    }
}