package com.example.kusithms_hdmedi_project.view.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.base.BaseDialog
import com.example.kusithms_hdmedi_project.databinding.ActivityHospitalBinding
import com.example.kusithms_hdmedi_project.databinding.ActivityWriteReviewBinding

class WriteReviewActivity : AppCompatActivity() {
    private var _binding : ActivityWriteReviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener {
            BaseDialog(
                context = this@WriteReviewActivity,
                title = resources.getString(R.string.후기_작성을_종료하시겠습니까_),
                subTitle = "",
                positive = {
                    finish()
                },
                negative = {}
            ).show(supportFragmentManager, "")
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}