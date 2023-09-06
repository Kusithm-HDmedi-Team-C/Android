package com.example.kusithms_hdmedi_project.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityDiagnosisBinding
import com.example.kusithms_hdmedi_project.databinding.FragmentDiagnosisPrevBinding

class DiagnosisPrevFragment: Fragment() {
    private var _binding : FragmentDiagnosisPrevBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDiagnosisPrevBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}