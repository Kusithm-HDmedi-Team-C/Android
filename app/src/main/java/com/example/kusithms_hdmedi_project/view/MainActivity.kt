package com.example.kusithms_hdmedi_project.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}