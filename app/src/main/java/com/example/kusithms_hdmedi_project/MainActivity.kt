package com.example.kusithms_hdmedi_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kusithms_hdmedi_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tv.text = "2312423"
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}