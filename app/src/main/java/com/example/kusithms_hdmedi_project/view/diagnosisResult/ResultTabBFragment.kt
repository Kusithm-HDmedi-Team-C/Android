package com.example.kusithms_hdmedi_project.view.diagnosisResult

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.kusithms_hdmedi_project.R
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
        val arrow1 = binding.tiptitle1
        val arrow2 = binding.tiptitle3
        val arrow4 = binding.tiptitle4
        val arrow5 = binding.tiptitle5
        val arrow6 = binding.tiptitle6
        val arrow7 = binding.tiptitle7
        val arrow8 = binding.tiptitle2

        arrow1.setOnClickListener {
            viewModel.isclose1()
        }
        arrow2.setOnClickListener {
            viewModel.isclose2()
        }
        arrow4.setOnClickListener {
            viewModel.isclose4()
        }
        arrow5.setOnClickListener {
            viewModel.isclose5()
        }
        arrow6.setOnClickListener {
            viewModel.isclose6()
        }
        arrow7.setOnClickListener {
            viewModel.isclose7()
        }
        arrow8.setOnClickListener {
            viewModel.isclose8()
        }


        getBoldText(binding.tipdetail1,getString(R.string.tipdetail1),listOf("‘주의전환’ 능력"))
        getBoldText(binding.tipdetail2,getString(R.string.tipdetail2),listOf("1) 주의력 결핍 유형, 2) 과잉행동・충동성 유형, 3) 혼합형"))
        getBoldText(binding.tipdetail3,getString(R.string.tipdetail3),listOf("가장 효과적인 방법"))
        getBoldText(binding.tipdetail5,getString(R.string.tipdetail5),listOf("주의 집중 능력을 조절하는 신경전달 물질이 불균형"))
        getBoldText(binding.tipdetail6,getString(R.string.tipdetail6),listOf("자녀의 연령과 발달 수준, 성향을 고려하여 진료를 받는 이유"))
        getBoldText(binding.tipdetail7,getString(R.string.tipdetail7),listOf("ADHD","아이의 특성을 고려하여 적절하게 발달할 수 있도록 도와주시는 것","아이의 노력과 태도의 영역이 아님"))




    }

    fun getBoldText(textView: TextView, fullText:String, wordsToBold:List<String>)
    {
        val spannableStringBuilder =  SpannableStringBuilder(fullText)
        for(wordToBold in wordsToBold)
        {
            var startIndex = fullText.indexOf(wordToBold)
            while(startIndex != -1)
            {
                val endIndex = startIndex + wordToBold.length
                spannableStringBuilder.setSpan(StyleSpan(Typeface.BOLD),startIndex,endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                startIndex = fullText.indexOf(wordToBold, endIndex)
            }
        }
        textView.text = spannableStringBuilder
    }
}