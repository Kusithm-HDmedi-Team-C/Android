package com.example.kusithms_hdmedi_project.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityDiagnosisBinding
import com.example.kusithms_hdmedi_project.databinding.ActivityMainBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class DiagnosisActivity : AppCompatActivity() {
    private var _binding : ActivityDiagnosisBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDiagnosisBinding.inflate(layoutInflater)
        binding.activity = this@DiagnosisActivity
        setContentView(binding.root)

        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                // 상단바 접혔을 때
                binding.ivBack.setColorFilter(resources.getColor(R.color.black))
                binding.ivClose.setColorFilter(resources.getColor(R.color.black))
            } else {
                // 상단바 펴졌을 때
                binding.ivBack.setColorFilter(resources.getColor(R.color.white))
                binding.ivClose.setColorFilter(resources.getColor(R.color.white))
            }
        }

//        binding.appBarLayout.
    }

    fun selectAnswer(view: View) {
        when (view.id) {
            R.id.ll_answer_1 -> {
                binding.llAnswer1.setBackgroundResource(R.drawable.bg_diagnosis_question_check_on)
                binding.ivCheck1.setImageResource(R.drawable.ic_check_on)
                binding.tvAnswer1.setTextColor(Color.parseColor("#4E7FFF"))
            }
            R.id.ll_answer_2 -> {
                binding.llAnswer2.setBackgroundResource(R.drawable.bg_diagnosis_question_check_on)
                binding.ivCheck2.setImageResource(R.drawable.ic_check_on)
                binding.tvAnswer2.setTextColor(Color.parseColor("#4E7FFF"))
            }
            R.id.ll_answer_3 -> {
                binding.llAnswer3.setBackgroundResource(R.drawable.bg_diagnosis_question_check_on)
                binding.ivCheck3.setImageResource(R.drawable.ic_check_on)
                binding.tvAnswer3.setTextColor(Color.parseColor("#4E7FFF"))
            }
            R.id.ll_answer_4 -> {
                binding.llAnswer4.setBackgroundResource(R.drawable.bg_diagnosis_question_check_on)
                binding.ivCheck4.setImageResource(R.drawable.ic_check_on)
                binding.tvAnswer4.setTextColor(Color.parseColor("#4E7FFF"))
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}