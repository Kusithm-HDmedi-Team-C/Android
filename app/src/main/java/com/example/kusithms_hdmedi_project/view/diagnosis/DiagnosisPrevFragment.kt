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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDiagnosisPrevBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 최초 터치시에만 작동
        binding.llTop.setOnClickListener {
            setupVisibleView()
            binding.llTop.isClickable = false
        }

        binding.tvStart.setOnClickListener {
            startActivity(Intent(requireContext(), DiagnosisActivity::class.java))
            requireActivity().finish()
        }

    }

    /** 체크할 때 마다 다음단계의 View Visible, 애니메이션 처리 **/
    private fun setupVisibleView() {
        binding.ivCharacter.apply {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_to_left))
        }
        binding.llChat1.apply {
            startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
            visibility = View.VISIBLE
        }

        binding.ivCheck1.setOnClickListener {
            binding.ivCheck1.setImageResource(R.drawable.ic_check_on)
            binding.llChat2.apply {
                startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
                visibility = View.VISIBLE
            }
            binding.ivCheck1.isClickable = false
        }

        binding.ivCheck2.setOnClickListener {
            binding.ivCheck2.setImageResource(R.drawable.ic_check_on)
            binding.llChat3.apply {
                startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
                visibility = View.VISIBLE
            }
            binding.ivCheck2.isClickable = false
        }

        binding.ivCheck3.setOnClickListener {
            binding.ivCheck3.setImageResource(R.drawable.ic_check_on)
            binding.tvStart.apply {
                startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
                visibility = View.VISIBLE
            }
            binding.ivCheck3.isClickable = false
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}