package com.example.kusithms_hdmedi_project.view.hospital.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.model.Hospital
import com.example.kusithms_hdmedi_project.model.Hospitals

class searchRecyclerAdapter(private var hospitals:List<Hospitals>,
                            private val listener:onHospitalItemClickListener):
    RecyclerView.Adapter<searchRecyclerAdapter.ViewHolder>() {
        class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
        {
            val hospitalTitle:TextView = itemView.findViewById(R.id.hospitalTitle)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.searchlist,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hospital = hospitals[position]
        holder.hospitalTitle.text = hospital.name
        holder.itemView.setOnClickListener{
            listener.onHospitalItemClicked(hospital.name)
        }
    }

    override fun getItemCount(): Int {
        return hospitals.size
    }
    fun updateSearchList(newData:List<Hospitals>)
    {
        this.hospitals = newData
        notifyDataSetChanged()
    }
}