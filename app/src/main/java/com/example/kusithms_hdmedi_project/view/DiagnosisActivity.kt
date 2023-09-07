package com.example.kusithms_hdmedi_project.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.base.BaseDialog
import com.example.kusithms_hdmedi_project.databinding.ActivityDiagnosisBinding
import com.example.kusithms_hdmedi_project.databinding.ActivityMainBinding
import com.example.kusithms_hdmedi_project.viewmodel.DiagnosisViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.abs

class DiagnosisActivity : AppCompatActivity() {
    private var _binding : ActivityDiagnosisBinding? = null
    private val binding get() = _binding!!
    val diagnosisVM by viewModels<DiagnosisViewModel>()

    private var nowQuestionIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDiagnosisBinding.inflate(layoutInflater)
        binding.activity = this@DiagnosisActivity
        setContentView(binding.root)

        diagnosisVM.getQuestionList()


        /** 상단 앱바 펼쳐지거나 접히는거 리스너 **/
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

        binding.ivClose.setOnClickListener {
            val dialog = BaseDialog(
                this,
                getString(R.string.자가진단을_종료하시겠습니까_),
                getString(R.string.기록된_사항은_저장되지_않아요),
                positive = {
                    finish()
                    Toast.makeText(this, "중단되었습니다.", Toast.LENGTH_SHORT).show()
                },
                negative = {
                    Toast.makeText(this, "취소취소", Toast.LENGTH_SHORT).show()
                }
            )
            dialog.show(supportFragmentManager, "")
        }

        binding.ivBack.setOnClickListener {
            diagnosisVM.prevQuestion(--nowQuestionIndex)
        }

        /** 모든 설문 문항 **/
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                diagnosisVM.questionList.collect {
                    diagnosisVM.nextQuestion(++nowQuestionIndex)
                }
            }
        }

        /** 현재 사용자가 보고 있는 문항 **/
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                diagnosisVM.nowQuestion.collect {
                    binding.tvQuestionNumber.text = (nowQuestionIndex+1).toString() +"."
                    binding.tvQuestion.text = it
                }
            }
        }
        diagnosisVM.pbStepSize.observe(this) {
            binding.pbDiagnosis.progress = it

        }
    }

    /** 답변 선택 **/
    fun selectAnswer(view: View) {
        binding.appBarLayout.setExpanded(false, true)

        when (view.id) {
            R.id.ll_answer_1 -> {
                refreshAnswerBoxUi(binding.llAnswer1, binding.ivCheck1, binding.tvAnswer1, true)
            }
            R.id.ll_answer_2 -> {
                refreshAnswerBoxUi(binding.llAnswer2, binding.ivCheck2, binding.tvAnswer2, true)
            }
            R.id.ll_answer_3 -> {
                refreshAnswerBoxUi(binding.llAnswer3, binding.ivCheck3, binding.tvAnswer3, true)
            }
            R.id.ll_answer_4 -> {
                refreshAnswerBoxUi(binding.llAnswer4, binding.ivCheck4, binding.tvAnswer4, true)
            }
        }
    }

    /** 체크된 답변 UI 강조하고 초기화 **/
    private fun refreshAnswerBoxUi(
        llAnswer: View,
        ivCheck : ImageView,
        tvAnswer : TextView,
        checked: Boolean
    ) {
        if (checked) {
            llAnswer.setBackgroundResource(R.drawable.bg_diagnosis_question_check_on)
            ivCheck.setImageResource(R.drawable.ic_check_on)
            tvAnswer.setTextColor(Color.parseColor("#4E7FFF"))

            lifecycleScope.launch {
                delay(600)
                refreshAnswerBoxUi(llAnswer, ivCheck, tvAnswer, false)

                diagnosisVM.nextQuestion(++nowQuestionIndex)
            }
        } else {
            llAnswer.setBackgroundResource(R.drawable.bg_diagnosis_question_check_off)
            ivCheck.setImageResource(R.drawable.ic_check_off)
            tvAnswer.setTextColor(Color.parseColor("#8D94A0"))
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}