package com.example.kusithms_hdmedi_project.view.hospital.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ItemSpinnerOptionBinding

class SpinnerAdapter(context: Context, @LayoutRes private val resId:Int, private val menuList:List<String>)
    :ArrayAdapter<String>(context,resId,menuList)
{
    var selectedPosition = -1
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerOptionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        binding.itemlist.text = menuList[position]
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerOptionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        binding.itemlist.text = menuList[position]

//        if(position ==1)
//        {
//            binding.itemlist.setTextColor(ContextCompat.getColor(context, R.color.questiontitle))
//        }
//        else
//        {
//            binding.itemlist.setTextColor(ContextCompat.getColor(context, R.color.questiontitle))
//        }
        if(position == selectedPosition)
        {
            binding.itemlist.setTextColor(ContextCompat.getColor(context, R.color.sub_blue))
        }
        else
        {
            binding.itemlist.setTextColor(ContextCompat.getColor(context, R.color.questiontitle))
        }
        return binding.root
    }

    override fun getCount(): Int {
        return menuList.size
    }

    }
