package com.example.kusithms_hdmedi_project.view.hospital.review

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.base.BaseDialog
import com.example.kusithms_hdmedi_project.databinding.ActivityImageUploadBinding

class ImageUploadActivity : AppCompatActivity() {
    private var _binding : ActivityImageUploadBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvUploadImage.setOnClickListener {
            imageUploadComplete()
        }

        binding.btnClose.setOnClickListener {
            BaseDialog(
                context = this@ImageUploadActivity,
                title = resources.getString(R.string.후기_작성을_종료하시겠습니까_),
                subTitle = "",
                positive = {
                    finish()
                },
                negative = {}
            ).show(supportFragmentManager, "")
        }
    }

    private fun imageUploadComplete() {
        binding.llImageUploadOff.visibility = View.INVISIBLE
        binding.llImageUploadOn.visibility = View.VISIBLE
        binding.tvWriteReview.apply {
            isClickable = true
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@ImageUploadActivity, R.color.main_blue))

            setOnClickListener {
                startActivity(Intent(this@ImageUploadActivity, WriteReviewActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}