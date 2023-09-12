package com.example.kusithms_hdmedi_project.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityMainBinding
import com.example.kusithms_hdmedi_project.view.diagnosis.DiagnosisPrevActivity
import com.example.kusithms_hdmedi_project.view.hospital.HospitalActivity


class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDiagnosis.setOnClickListener {
            startActivity(Intent(this, DiagnosisPrevActivity::class.java))
        }

        binding.btnHospital.setOnClickListener {
            startActivity(Intent(this, HospitalActivity::class.java))
        }

        val str1 = resources.getString(R.string.ADHD_어디에서_진단받지)
        val ssb1 = SpannableStringBuilder(str1)
        val str2 = resources.getString(R.string.ADHD_어떻게_치료해야_할까_)
        val ssb2 = SpannableStringBuilder(str2)
        ssb1.setSpan(ForegroundColorSpan(Color.parseColor("#4E7FFF")),11,13,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvHome2.text = ssb1

        ssb2.setSpan(ForegroundColorSpan(Color.parseColor("#4E7FFF")),10,12,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvHome3.text = ssb2
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}