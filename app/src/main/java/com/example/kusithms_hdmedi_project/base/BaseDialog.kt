package com.example.kusithms_hdmedi_project.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityDiagnosisBinding
import com.example.kusithms_hdmedi_project.databinding.DialogBaseBinding

class BaseDialog(
    context: Context,
    val title: String,
    val subTitle: String,
    val positive: () -> Unit,
    val negative: () -> Unit
): DialogFragment() {
    private var _binding : DialogBaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogBaseBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = title

        if (subTitle.isEmpty()) {
            binding.tvSubTitle.visibility = View.GONE
        } else {
            binding.tvSubTitle.text = subTitle
        }

        // 확인, Positive 버튼 터치 시 람다로 들어온 positive() 실행
        binding.btnOk.setOnClickListener {
            dismiss()
            positive()
        }

        // 취소, Negative 버튼 터치 시 람다로 들어온 negative() 실행
        binding.btnCancel.setOnClickListener {
            dismiss()
            negative()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}