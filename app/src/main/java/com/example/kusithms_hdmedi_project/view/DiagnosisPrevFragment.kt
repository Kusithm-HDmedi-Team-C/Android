package com.example.kusithms_hdmedi_project.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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

        binding.llTop.setOnClickListener {
            // 화면 어디든 터치하면 다음 View 보이도록 설정
            setupVisibleView()
            binding.llTop.isClickable = false
        }

        binding.tvStart.setOnClickListener {
            startActivity(Intent(requireContext(), DiagnosisActivity::class.java))
            requireActivity().finish()
        }
    }

    /** 화면 터칠할 때마다 다음단계의 View Visible, 애니메이션 처리 **/
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
        }

        binding.ivCheck2.setOnClickListener {
            binding.ivCheck2.setImageResource(R.drawable.ic_check_on)
            binding.llChat3.apply {
                startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
                visibility = View.VISIBLE
            }
        }

        binding.ivCheck3.setOnClickListener {
            binding.ivCheck3.setImageResource(R.drawable.ic_check_on)
            binding.tvStart.apply {
                startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
                visibility = View.VISIBLE
            }
        }

//        when (touchStatus) {
//            0 -> {
//                binding.ivCharacter.apply {
//                    visibility = View.VISIBLE
//                    startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right_to_left))
//                }
//                binding.llChat1.apply {
//                    startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
//                    visibility = View.VISIBLE
//                }
//            }
//            1 -> {
//                binding.llChat2.apply {
//                    startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
//                    visibility = View.VISIBLE
//                }
//            }
//            2 -> {
//                binding.llChat3.apply {
//                    startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
//                    visibility = View.VISIBLE
//                }
//                binding.tvStart.apply {
//                    startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in))
//                    visibility = View.VISIBLE
//                }
//            }
//        }
//        touchStatus++
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}