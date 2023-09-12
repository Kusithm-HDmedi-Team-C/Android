package com.example.kusithms_hdmedi_project.view.diagnosis

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.api.RequestBodyModel
import com.example.kusithms_hdmedi_project.base.BaseDialog
import com.example.kusithms_hdmedi_project.databinding.ActivityDiagnosisBinding
import com.example.kusithms_hdmedi_project.util.Extensions.repeatOnStarted
import com.example.kusithms_hdmedi_project.viewmodel.DiagnosisViewModel
import kotlin.math.abs

class DiagnosisActivity : AppCompatActivity() {
    private var _binding : ActivityDiagnosisBinding? = null
    private val binding get() = _binding!!
    val diagnosisVM by viewModels<DiagnosisViewModel>()

    private lateinit var diagnosisContentsViewPagerAdapter: DiagnosisContentsViewPagerAdapter
    private var nowPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDiagnosisBinding.inflate(layoutInflater)
        binding.activity = this@DiagnosisActivity
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.fl_init, DiagnosisInitFragment()).commit()

        diagnosisVM.getQuestionList()
        subscribeUi()

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

        /** 설문 정보를 담고 있는 뷰페이저 리스너 **/
        binding.vpDiagnosisContents.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                diagnosisVM.refreshPb(position+1)
                nowPage = position

//                // 답변 선택하지 않은 경우
//                if (diagnosisVM.answerList.value[position] == -1) {
//                    if (position > binding.vpDiagnosisContents.currentItem) {
//                        binding.vpDiagnosisContents.isUserInputEnabled = false
//                    }
//                }

                binding.vpDiagnosisContents.isUserInputEnabled = diagnosisVM.answerList.value[position] != -1

                diagnosisContentsViewPagerAdapter.setAnswerList(diagnosisVM.answerList.value)
            }
        })

        binding.ivClose.setOnClickListener {
             BaseDialog(
                this,
                getString(R.string.자가진단을_종료하시겠습니까_),
                getString(R.string.기록된_사항은_저장되지_않아요),
                positive = {
                    finish()
                },
                negative = {}
            ).show(supportFragmentManager, "")
        }
    }

    private fun subscribeUi() {
        /** 모든 설문 문항 **/
        repeatOnStarted {
            diagnosisVM.questionList.collect {
                diagnosisContentsViewPagerAdapter = DiagnosisContentsViewPagerAdapter(this@DiagnosisActivity, it)
                binding.vpDiagnosisContents.adapter = diagnosisContentsViewPagerAdapter

                diagnosisContentsViewPagerAdapter.setItemClickListener(object :
                    DiagnosisContentsViewPagerAdapter.OnItemClickListener {
                    override fun onClickAnswer(v: View, position: Int, answerNumber: Int) {
                        binding.appBarLayout.setExpanded(false, true)
                        diagnosisVM.setAnswerList(position, answerNumber)
                        diagnosisContentsViewPagerAdapter.setAnswerList(diagnosisVM.answerList.value)
                    }

                    override fun onclickFinish(requestBody: RequestBodyModel) {
                        binding.appBarLayout.visibility = View.GONE
                        supportFragmentManager.beginTransaction().add(R.id.fl_init, DiagnosisLoadingFragment().apply {
                            val bundle = Bundle()
                            bundle.putSerializable("requestBody", requestBody)
                            arguments = bundle
                        }).commit()
                    }
                })
            }
        }

        // 답변 목록이 유지되는 옵저빙
        repeatOnStarted {
            diagnosisVM.answerList.collect {
                binding.vpDiagnosisContents.isUserInputEnabled = it[nowPage] != -1
                var sum = 0
                it.map { sum+=it }
            }
        }

        // 상단에 설문 진행률 프로그레스바
        diagnosisVM.pbStepSize.observe(this) {
            binding.pbDiagnosis.progress = it
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}