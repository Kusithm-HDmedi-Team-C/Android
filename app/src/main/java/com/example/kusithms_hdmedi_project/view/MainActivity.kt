package com.example.kusithms_hdmedi_project.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityMainBinding
import com.example.kusithms_hdmedi_project.view.diagnosis.DiagnosisPrevActivity
import com.example.kusithms_hdmedi_project.view.hospital.search.HospitalActivity
import com.example.kusithms_hdmedi_project.view.hospital.review.ImageUploadActivity


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

        binding.btnHospitalDiagnosis.setOnClickListener {
            startActivity(Intent(this, HospitalActivity::class.java))
        }

        binding.btnReviewWrite.setOnClickListener {
            startActivity(Intent(this, ImageUploadActivity::class.java))
        }

        val str1 = resources.getString(R.string.ADHD_어디에서_진단받지)
        val ssb1 = SpannableStringBuilder(str1)
        val str2 = resources.getString(R.string.ADHD_부작용_없이_치료할_수_있을까_)
        val ssb2 = SpannableStringBuilder(str2)
        ssb1.setSpan(ForegroundColorSpan(Color.parseColor("#4E7FFF")),11,13,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvHome2.text = ssb1

        ssb2.setSpan(ForegroundColorSpan(Color.parseColor("#4E7FFF")),13,15,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvHome3.text = ssb2

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}