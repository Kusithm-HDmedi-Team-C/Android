package com.example.kusithms_hdmedi_project.view.diagnosis

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.model.ApiResponse
import com.example.kusithms_hdmedi_project.model.RequestBodyModel
import com.example.kusithms_hdmedi_project.view.diagnosisResult.DiagnosisResultActivity
import com.example.kusithms_hdmedi_project.viewmodel.DiagnosisViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DiagnosisLoadingFragment : Fragment() {
    private lateinit var requestBody: RequestBodyModel
    private val viewModel: DiagnosisViewModel by viewModels()
    private lateinit var diagnosisResult : ApiResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_diagnosis_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestBody = arguments?.getSerializable("requestBody") as RequestBodyModel

        viewModel.postDataToResponse(requestBody)

        lifecycleScope.launch {
            delay(1500)
            finishDiagnosis()
        }

        viewModel.diagnosisResult.observe(viewLifecycleOwner) {
            diagnosisResult = it
        }
    }

    private fun finishDiagnosis() {
        startActivity(Intent(requireContext(), DiagnosisResultActivity::class.java).apply {
            putExtra("answer_list", requestBody)
            putExtra("diagnosis_result", diagnosisResult)
        })
        requireActivity().finish()
    }
}