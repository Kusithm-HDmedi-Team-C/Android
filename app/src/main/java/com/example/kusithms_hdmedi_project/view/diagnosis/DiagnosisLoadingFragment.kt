package com.example.kusithms_hdmedi_project.view.diagnosis

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.api.RequestBodyModel
import com.example.kusithms_hdmedi_project.view.diagnosisResult.DiagnosisResultActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DiagnosisLoadingFragment : Fragment() {
    private lateinit var requestBody: RequestBodyModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_diagnosis_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestBody = arguments?.getSerializable("requestBody") as RequestBodyModel

        lifecycleScope.launch {
            delay(1500)
            finishDiagnosis()
        }
    }

    private fun finishDiagnosis() {
        startActivity(Intent(requireContext(), DiagnosisResultActivity::class.java).apply {
            putExtra("answer_list", requestBody)
        })
        requireActivity().finish()
    }
}