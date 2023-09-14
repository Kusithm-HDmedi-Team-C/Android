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
import androidx.recyclerview.widget.LinearLayoutManager
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

    private lateinit var searchHospitalAdapter: SearchHospitalAdapter

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       _binding = FragmentReviewStep1Binding.inflate(inflater, container, false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSearchHospital.bringToFront()

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    binding.ivSearch.setImageResource(R.drawable.ic_search)
                } else {
                    binding.ivSearch.setImageResource(R.drawable.ic_close)
                    binding.rvSearchHospital.visibility = View.VISIBLE

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
                updateNextButton()
            }
        })

        searchHospitalAdapter = SearchHospitalAdapter(requireContext())

        binding.rvSearchHospital.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchHospitalAdapter
        }

        searchHospitalAdapter.setItemClickListener(object : SearchHospitalAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int, hospitalId: Int) {
                binding.etSearch.setText(viewmodel.searchedHospitalList.value.hospitals[position].name)
                searchHospitalAdapter.setData(emptyList())
                binding.rvSearchHospital.visibility = View.GONE

                viewmodel.writeReviewBody.update { viewmodel.writeReviewBody.value.copy(hospitalId = hospitalId) }
                updateNextButton()
            }
        })

        repeatOnStarted {
            viewmodel.searchedHospitalList.collect {
                searchHospitalAdapter.setData(it.hospitals)
            }

        }
    }

    private fun updateNextButton() {
        viewmodel.step1Status.value = viewmodel.writeReviewBody.value.hospitalId != 0 && viewmodel.writeReviewBody.value.contents.isNotEmpty()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}