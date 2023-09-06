package com.example.kusithms_hdmedi_project.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityDiagnosisBinding
import com.example.kusithms_hdmedi_project.databinding.ActivityMainBinding

class DiagnosisActivity : AppCompatActivity() {
    private var _binding : ActivityDiagnosisBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDiagnosisBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}