package com.example.kusithms_hdmedi_project.view.hospital.review

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.base.BaseDialog
import com.example.kusithms_hdmedi_project.databinding.ActivityImageUploadBinding
import com.example.kusithms_hdmedi_project.databinding.ActivityWriteReviewBinding
import com.example.kusithms_hdmedi_project.util.Extensions.repeatOnStarted
import com.example.kusithms_hdmedi_project.viewmodel.ReviewViewModel
import kotlinx.coroutines.flow.collect

class WriteReviewActivity : AppCompatActivity() {
    private var _binding : ActivityWriteReviewBinding? = null
    private val binding get() = _binding!!
    val viewmodel by viewModels<ReviewViewModel>()


    private var nowPage = 1

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
            if (nowPage == 1) {
                supportFragmentManager.beginTransaction().replace(R.id.fl_top, ReviewStep2Fragment()).commit()
                binding.tvNext.text = "완료"
                nowPage = 2
            } else if (nowPage == 2) {
                val value = viewmodel.writeReviewBody.value
                if (value.rating != 0 && value.doctor.isNotEmpty() && value.price != 0 && value.examinations.isNotEmpty()) {
                    val bitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.jpg_img)
                    viewmodel.postReview(bitmap)
                }
            }
        }
    }

    private fun subscribeUi() {
        viewmodel.step1Status.observe(this) {
            if (it) {
                binding.tvNext.setTextColor(ContextCompat.getColor(this@WriteReviewActivity, R.color.sub_blue))
            } else {
                binding.tvNext.setTextColor(ContextCompat.getColor(this@WriteReviewActivity, R.color.fullscore))
            }
        }

        viewmodel.writeReviewResponse.observe(this) {
            if (it == 200) {
                finish()
                Toast.makeText(this@WriteReviewActivity, "리뷰 작성 완료", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@WriteReviewActivity, "리뷰 작성 실패", Toast.LENGTH_SHORT).show()
            }
        }

        repeatOnStarted {
            viewmodel.writeReviewBody.collect {
                binding.tvNext.isClickable = it.contents.isNotEmpty()
            }
        }
    }



    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}