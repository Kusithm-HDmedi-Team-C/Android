package com.example.kusithms_hdmedi_project.view.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityHospitalBinding
import com.example.kusithms_hdmedi_project.databinding.ActivityMainBinding

class HospitalActivity : AppCompatActivity() {
    private var _binding : ActivityHospitalBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}