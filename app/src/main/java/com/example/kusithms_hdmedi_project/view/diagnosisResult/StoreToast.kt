package com.example.kusithms_hdmedi_project.view.diagnosisResult

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ToastmessageBinding

object StoreToast {
    fun createToast(context: Context, message:String): Toast?{
        val inflater = LayoutInflater.from(context)
        val binding:ToastmessageBinding = DataBindingUtil.inflate(inflater, R.layout.toastmessage,null,false)

        binding.message.text = message
        return Toast(context).apply {
            duration = Toast.LENGTH_LONG
            view=binding.root
        }
    }
    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}