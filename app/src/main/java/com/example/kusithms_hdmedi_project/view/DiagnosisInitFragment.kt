package com.example.kusithms_hdmedi_project.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kusithms_hdmedi_project.databinding.FragmentDiagnosisInitBinding

class DiagnosisInitFragment: Fragment() {
    private var _binding : FragmentDiagnosisInitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDiagnosisInitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clTop.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}