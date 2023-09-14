package com.example.kusithms_hdmedi_project.view.hospital.review

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update

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

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    binding.ivSearch.setImageResource(R.drawable.ic_search)
                } else {
                    binding.ivSearch.setImageResource(R.drawable.ic_close)

                    viewmodel.searchHospitalsFromName(p0.toString())
                }
            }
        })
        binding.ivSearch.setOnClickListener {
            if (binding.etSearch.text.isNotEmpty()) {
                binding.etSearch.setText("")
                binding.ivSearch.setImageResource(R.drawable.ic_search)
            }
        }

        binding.etReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvReviewCnt.text = "${p0?.length.toString()} / 1000"

                viewmodel.writeReviewBody.update { viewmodel.writeReviewBody.value.copy(contents = p0.toString()) }
            }
        })

        repeatOnStarted {
            viewmodel.searchedHospitalList.collect {
                Log.d("taag", it.toString())
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}