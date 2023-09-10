package com.example.kusithms_hdmedi_project.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.api.RequestBodyModel
import com.example.kusithms_hdmedi_project.databinding.FragmentResultTabABinding
import com.example.kusithms_hdmedi_project.viewmodel.DiagnosisResultViewModel

class ResultTabAFragment : Fragment() {
    private var _binding : FragmentResultTabABinding? = null
    private val viewModel: DiagnosisResultViewModel by viewModels()
    private val binding get() = _binding!!

    private lateinit var requestBody : RequestBodyModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestBody = arguments?.getSerializable("answer_list") as RequestBodyModel

        _binding = FragmentResultTabABinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainCharacter.startAnimation(
            android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.jump))

        Log.d("taag", requestBody.toString())
        viewModel.postDataApi(requestBody)

        viewModel.totalscore.observe(viewLifecycleOwner, Observer{
                totalscore->
            binding.score.text = totalscore.toString()
        })
        viewModel.careless.observe(viewLifecycleOwner, Observer{
                careless->
            binding.uncarefulScore.text = careless.toString()
        })
        viewModel.impulsitive.observe(viewLifecycleOwner,Observer{
                impulsitive->
            binding.activityScore.text = impulsitive.toString()
        })

    }

    companion object {
        fun newInstance(requestBody: RequestBodyModel): ResultTabAFragment {
            val fragment = ResultTabAFragment().apply {
                val bundle = Bundle()
                bundle.putSerializable("answer_list", requestBody)
                arguments = bundle
            }
            return fragment
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}