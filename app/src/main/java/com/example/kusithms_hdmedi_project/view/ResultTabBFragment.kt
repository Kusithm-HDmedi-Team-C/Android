package com.example.kusithms_hdmedi_project.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.FragmentResultTabABinding
import com.example.kusithms_hdmedi_project.databinding.FragmentResultTabBBinding
import com.example.kusithms_hdmedi_project.viewmodel.DiagnosisResultViewModel


class ResultTabBFragment : Fragment() {
    private var _binding: FragmentResultTabBBinding? = null
    private val viewModel: DiagnosisResultViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultTabBBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arrow1 = binding.arrow1
        val arrow2 = binding.arrow2
        val arrow4 = binding.arrow4
        val arrow5 = binding.arrow5
        val arrow6 = binding.arrow6
        val arrow7 = binding.arrow7

        arrow1.setOnClickListener {
            viewModel.isclose()
        }
        arrow2.setOnClickListener {
            viewModel.isclose()
        }
        arrow4.setOnClickListener {
            viewModel.isclose()
        }
        arrow5.setOnClickListener {
            viewModel.isclose()
        }
        arrow6.setOnClickListener {
            viewModel.isclose()
        }
        arrow7.setOnClickListener {
            viewModel.isclose()
        }


    }
}