package com.example.kusithms_hdmedi_project.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
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
            resetAnswerBoxUi()
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
                selectedAnswerBoxUi(binding.llAnswer1, binding.ivCheck1, binding.tvAnswer1)
            }
            R.id.ll_answer_2 -> {
                selectedAnswerBoxUi(binding.llAnswer2, binding.ivCheck2, binding.tvAnswer2)
            }
            R.id.ll_answer_3 -> {
                selectedAnswerBoxUi(binding.llAnswer3, binding.ivCheck3, binding.tvAnswer3)
            }
            R.id.ll_answer_4 -> {
                selectedAnswerBoxUi(binding.llAnswer4, binding.ivCheck4, binding.tvAnswer4)
            }
        }
        
    }

    /** 체크된 답변 UI 강조하고 초기화 **/
    private fun selectedAnswerBoxUi(
        llAnswer: View,
        ivCheck : ImageView,
        tvAnswer : TextView,
    ) {
        window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        if (nowQuestionIndex+1 == diagnosisVM.questionList.value.size) {
            // 마지막 문항인 경우
            binding.tvFinish.visibility = View.VISIBLE
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            resetAnswerBoxUi()
        } else {
            lifecycleScope.launch {
                delay(500)

                diagnosisVM.nextQuestion(++nowQuestionIndex)
                window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                resetAnswerBoxUi()
            }
        }

        llAnswer.setBackgroundResource(R.drawable.bg_diagnosis_question_check_on)
        ivCheck.setImageResource(R.drawable.ic_check_on)
        tvAnswer.setTextColor(Color.parseColor("#4E7FFF"))
    }

    /** 체크된 답변 UI상태 초기화하는 함수 **/
    private fun resetAnswerBoxUi() {
        binding.llAnswer1.setBackgroundResource(R.drawable.bg_diagnosis_question_check_off)
        binding.llAnswer2.setBackgroundResource(R.drawable.bg_diagnosis_question_check_off)
        binding.llAnswer3.setBackgroundResource(R.drawable.bg_diagnosis_question_check_off)
        binding.llAnswer4.setBackgroundResource(R.drawable.bg_diagnosis_question_check_off)
        binding.ivCheck1.setImageResource(R.drawable.ic_check_off)
        binding.ivCheck2.setImageResource(R.drawable.ic_check_off)
        binding.ivCheck3.setImageResource(R.drawable.ic_check_off)
        binding.ivCheck4.setImageResource(R.drawable.ic_check_off)
        binding.tvAnswer1.setTextColor(Color.parseColor("#8D94A0"))
        binding.tvAnswer2.setTextColor(Color.parseColor("#8D94A0"))
        binding.tvAnswer3.setTextColor(Color.parseColor("#8D94A0"))
        binding.tvAnswer4.setTextColor(Color.parseColor("#8D94A0"))
    }

    fun finishDiagnosis() {
        startActivity(Intent(this, DiagnosisResultActivity::class.java).apply {

        })
        finish()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}