package com.example.kusithms_hdmedi_project.view.diagnosisResult

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.viewmodel.DiagnosisResultViewModel


class MiniFragment : DialogFragment() {
    private val viewModel: DiagnosisResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onResume() {
        super.onResume()
        val window = dialog?.window ?: return
        val params = window.attributes
        params.x = viewModel.location[0]
        params.y =viewModel.location[1]
        window.attributes = params

    }

}