package com.example.kusithms_hdmedi_project.view.diagnosis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityDiagnosisPrevBinding

class DiagnosisPrevActivity : AppCompatActivity() {
    private var _binding : ActivityDiagnosisPrevBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDiagnosisPrevBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 첫 진입할 때 확인사항 띄워주기
        supportFragmentManager.beginTransaction().add(R.id.framelayout, DiagnosisPrevFragment()).commit()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}