package com.example.kusithms_hdmedi_project.view.hospital.review

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.base.BaseDialog
import com.example.kusithms_hdmedi_project.databinding.ActivityImageUploadBinding
import com.example.kusithms_hdmedi_project.databinding.ActivityWriteReviewBinding
import com.example.kusithms_hdmedi_project.util.Extensions.repeatOnStarted
import com.example.kusithms_hdmedi_project.viewmodel.ReviewViewModel

class WriteReviewActivity : AppCompatActivity() {
    private var _binding : ActivityWriteReviewBinding? = null
    private val binding get() = _binding!!
    val viewmodel by viewModels<ReviewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fl_top, ReviewStep1Fragment()).commit()
        subscribeUi()

        binding.ivBack.setOnClickListener {
            BaseDialog(
                context = this@WriteReviewActivity,
                title = "후기 작성을 종료하시겠습니까?",
                subTitle = "기록된 사항은 저장되지 않아요.",
                positive = { finish() },
                negative = {}
            ).show(supportFragmentManager, "")
        }

        binding.tvNext.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fl_top, ReviewStep2Fragment()).commit()
        }
    }

    private fun subscribeUi() {

        repeatOnStarted {
            viewmodel.writeReviewBody.collect {
                if (it.contents.isNotEmpty()) {
                    binding.tvNext.apply {
                        isClickable = true
                        setTextColor(ContextCompat.getColor(this@WriteReviewActivity, R.color.sub_blue))
                    }
                } else {
                    binding.tvNext.apply {
                        isClickable = false
                        setTextColor(ContextCompat.getColor(this@WriteReviewActivity, R.color.fullscore))
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}