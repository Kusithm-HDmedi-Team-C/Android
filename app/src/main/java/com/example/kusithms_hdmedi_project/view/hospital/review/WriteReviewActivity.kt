package com.example.kusithms_hdmedi_project.view.hospital.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.base.BaseDialog
import com.example.kusithms_hdmedi_project.databinding.ActivityImageUploadBinding
import com.example.kusithms_hdmedi_project.databinding.ActivityWriteReviewBinding

class WriteReviewActivity : AppCompatActivity() {
    private var _binding : ActivityWriteReviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            BaseDialog(
                context = this@WriteReviewActivity,
                title = "후기 작성을 종료하시겠습니까?",
                subTitle = "기록된 사항은 저장되지 않아요.",
                positive = { finish() },
                negative = {}
            ).show(supportFragmentManager, "")
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}