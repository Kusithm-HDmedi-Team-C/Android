package com.example.kusithms_hdmedi_project.view.diagnosis

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.FragmentDiagnosisPrevBinding

class DiagnosisPrevFragment: Fragment() {
    private var _binding : FragmentDiagnosisPrevBinding? = null
    private val binding get() = _binding!!

    private var touchStatus = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDiagnosisPrevBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivCharacter.apply {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_to_left))
        }
        binding.llChat1.apply {
            startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
            visibility = View.VISIBLE
        }

        // 최초 터치시에만 작동
        binding.llTop.setOnClickListener {
            when (touchStatus) {
                0 -> {
                    binding.llChat2.apply {
                        startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
                        visibility = View.VISIBLE
                    }
                    touchStatus ++
                }
                1 -> {
                    binding.llChat3.apply {
                        startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
                        visibility = View.VISIBLE
                    }
                    binding.tvStart.apply {
                        startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
                        visibility = View.VISIBLE
                    }
                    binding.llTop.isClickable = false
                }
            }
        }

        binding.tvStart.setOnClickListener {
            startActivity(Intent(requireContext(), DiagnosisActivity::class.java))
            requireActivity().finish()
        }

        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}