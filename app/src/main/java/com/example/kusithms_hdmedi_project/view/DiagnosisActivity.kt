package com.example.kusithms_hdmedi_project.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.kusithms_hdmedi_project.R
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
    private var nowQuestionIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDiagnosisBinding.inflate(layoutInflater)
        binding.activity = this@DiagnosisActivity
        setContentView(binding.root)

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
            }
        })

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
    }

    private fun subscribeUi() {
        /** 모든 설문 문항 **/
        repeatOnStarted {
            diagnosisVM.questionList.collect {
                diagnosisVM.nextQuestion(++nowQuestionIndex)
                diagnosisContentsViewPagerAdapter = DiagnosisContentsViewPagerAdapter(this@DiagnosisActivity, it)

                binding.vpDiagnosisContents.adapter = diagnosisContentsViewPagerAdapter

                diagnosisContentsViewPagerAdapter.setItemClickListener(object : DiagnosisContentsViewPagerAdapter.OnItemClickListener {
                    override fun onClickAnswer1(v: View, position: Int) {
                        binding.appBarLayout.setExpanded(false, true)

                    }

                    override fun onClickAnswer2(v: View, position: Int) {
                        binding.appBarLayout.setExpanded(false, true)

                    }

                    override fun onClickAnswer3(v: View, position: Int) {
                        binding.appBarLayout.setExpanded(false, true)

                    }

                    override fun onClickAnswer4(v: View, position: Int) {
                        binding.appBarLayout.setExpanded(false, true)

                    }

                })
            }
        }

        // 상단에 설문 진행률 프로그레스
        diagnosisVM.pbStepSize.observe(this) {
            binding.pbDiagnosis.progress = it
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}