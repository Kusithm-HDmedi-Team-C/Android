package com.example.kusithms_hdmedi_project.view.hospital.review

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kusithms_hdmedi_project.databinding.ItemHospitalBinding
import com.example.kusithms_hdmedi_project.databinding.ItemHospitalSearchBarBinding
import com.example.kusithms_hdmedi_project.model.Hospital


class SearchHospitalAdapter(
    private val context: Context,
)
    : RecyclerView.Adapter<SearchHospitalAdapter.CustomViewHolder>() {
    private var searchResultList = listOf<Hospital>()
    private lateinit var itemClickListener: OnItemClickListener

    inner class CustomViewHolder(private val binding: ItemHospitalSearchBarBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Hospital) {
            binding.title.text = item.name
            binding.address.text = item.address
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(searchResultList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it,position, searchResultList[position].hospitalId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = ItemHospitalSearchBarBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(view)
    }

    fun setData(list: List<Hospital>) {
        searchResultList = list
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int, hospitalId : Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    override fun getItemCount() = searchResultList.size
}