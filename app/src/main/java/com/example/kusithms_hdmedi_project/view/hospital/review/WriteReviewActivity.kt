package com.example.kusithms_hdmedi_project.view.hospital.review

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.base.BaseDialog
import com.example.kusithms_hdmedi_project.databinding.ActivityImageUploadBinding
import com.example.kusithms_hdmedi_project.databinding.ActivityWriteReviewBinding
import com.example.kusithms_hdmedi_project.util.Extensions.repeatOnStarted
import com.example.kusithms_hdmedi_project.viewmodel.ReviewViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
            if (nowPage == 1) {
                BaseDialog(
                    context = this@WriteReviewActivity,
                    title = "후기 작성을 종료하시겠습니까?",
                    subTitle = "기록된 사항은 저장되지 않아요.",
                    positive = { finish() },
                    negative = {}
                ).show(supportFragmentManager, "")
            } else if (nowPage == 2) {
                supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(R.id.fl_top)!!).commit()
                binding.tvNext.setTextColor(ContextCompat.getColor(this@WriteReviewActivity, R.color.sub_blue))
                nowPage = 1
            }

        }

        binding.tvNext.setOnClickListener {
            if (nowPage == 1) {
                supportFragmentManager.beginTransaction().add(R.id.fl_top, ReviewStep2Fragment()).commit()
                binding.tvNext.text = "완료"
                binding.tvNext.setTextColor(ContextCompat.getColor(this@WriteReviewActivity, R.color.fullscore))
                nowPage = 2
            } else if (nowPage == 2) {
                val value = viewmodel.writeReviewBody.value
                if (value.rating != 0 && value.doctor.isNotEmpty() && value.price != 0 && value.examinations.isNotEmpty()) {
                    val bitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.jpg_img)
                    viewmodel.postReview(bitmap)
                    binding.llTop.isClickable = false
                } else {
                    Toast.makeText(this@WriteReviewActivity, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun subscribeUi() {
        viewmodel.step1Status.observe(this) {
            if (nowPage == 1) {
                if (it) {
                    binding.tvNext.setTextColor(ContextCompat.getColor(this@WriteReviewActivity, R.color.sub_blue))
                } else {
                    binding.tvNext.setTextColor(ContextCompat.getColor(this@WriteReviewActivity, R.color.fullscore))
                }
            }
        }

        viewmodel.writeReviewResponse.observe(this) {
            if (it == 200) {
                val customDialog = CustomDialog(this)
                customDialog.show()
                lifecycleScope.launch {
                    delay(2000L)
                    finish()
                }
            } else {
                Toast.makeText(this@WriteReviewActivity, "리뷰 작성 실패", Toast.LENGTH_SHORT).show()
            }
        }

        repeatOnStarted {
            viewmodel.writeReviewBody.collect {
                if (nowPage == 2) {
                    binding.tvNext.isClickable = it.contents.isNotEmpty()
                    if (it.rating != 0 && it.doctor.isNotEmpty() && it.price != 0 && it.examinations.isNotEmpty()) {
                        binding.tvNext.setTextColor(ContextCompat.getColor(this@WriteReviewActivity, R.color.main_blue))
                    } else {
                        binding.tvNext.setTextColor(ContextCompat.getColor(this@WriteReviewActivity, R.color.fullscore))
                    }
                }
            }
        }
    }

    inner class CustomDialog(context: Context) : Dialog(context) {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.dialog_review_finish)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}