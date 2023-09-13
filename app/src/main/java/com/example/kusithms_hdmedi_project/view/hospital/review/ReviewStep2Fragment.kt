package com.example.kusithms_hdmedi_project.view.hospital.review

import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kusithms_hdmedi_project.databinding.FragmentReviewStep2Binding
import java.text.DecimalFormat


class ReviewStep2Fragment : Fragment() {
    private var _binding : FragmentReviewStep2Binding? = null
    private val binding get() = _binding!!
    var strAmount = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentReviewStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ratingbar.setOnRatingBarChangeListener { ratingBar, fl, b ->

        }

        binding.etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!TextUtils.isEmpty(p0.toString()) && p0.toString() != strAmount) {
                    strAmount = makeStringComma(p0.toString().replace(",", ""))!!
                    binding.etPrice.setText(strAmount)
                    val editable = binding.etPrice.text
                    Selection.setSelection(editable, strAmount.length)
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.ivClearName.setOnClickListener {
            binding.etDocName.setText("")
        }

        binding.ivClearPrice.setOnClickListener {
            binding.etPrice.setText("")
            strAmount = ""
        }
    }

    private fun makeStringComma(str: String): String? {    // 천단위 콤마설정.
        if (str.isEmpty()) {
            return ""
        }
        val value = str.toLong()
        val format = DecimalFormat("###,###")
        return format.format(value)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}