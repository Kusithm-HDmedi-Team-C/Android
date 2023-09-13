package com.example.kusithms_hdmedi_project.view.hospital

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.model.Hospital

class HospitalAdapter(private val hospitals: List<Hospital>) :
    RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hospitalNameTextView: TextView = itemView.findViewById(R.id.title)
        val ratingTextView: TextView = itemView.findViewById(R.id.rating)
        val reviewsTextView: TextView = itemView.findViewById(R.id.review_num)
        val hospitalAddress:TextView = itemView.findViewById(R.id.address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hospital, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hospital = hospitals[position]

        holder.hospitalNameTextView.text = hospital.name
        holder.hospitalAddress.text = hospital.address
        holder.ratingTextView.text = hospital.averageRating.toString()
        holder.reviewsTextView.text = hospital.numberOfReviews.toString()
    }

    override fun getItemCount(): Int {
        return hospitals.size
    }
}