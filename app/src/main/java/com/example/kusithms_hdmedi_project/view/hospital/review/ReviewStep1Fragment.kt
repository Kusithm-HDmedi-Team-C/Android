package com.example.kusithms_hdmedi_project.view.hospital.review

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.FragmentDiagnosisInitBinding
import com.example.kusithms_hdmedi_project.databinding.FragmentReviewStep1Binding
import com.example.kusithms_hdmedi_project.util.Extensions.repeatOnStarted
import com.example.kusithms_hdmedi_project.viewmodel.ReviewViewModel

class ReviewStep1Fragment : Fragment() {
    private var _binding : FragmentReviewStep1Binding? = null
    private val binding get() = _binding!!
    val viewmodel by activityViewModels<ReviewViewModel>()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       _binding = FragmentReviewStep1Binding.inflate(inflater, container, false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvReviewCnt.text = "${p0?.length.toString()} / 1000"

                viewmodel.step1Complete(!p0.isNullOrEmpty())
            }

            override fun afterTextChanged(p0: Editable?) {}
        })


    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}