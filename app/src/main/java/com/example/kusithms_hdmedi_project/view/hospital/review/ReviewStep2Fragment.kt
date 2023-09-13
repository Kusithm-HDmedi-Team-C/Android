package com.example.kusithms_hdmedi_project.view.hospital.review

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.FragmentReviewStep2Binding
import com.example.kusithms_hdmedi_project.util.Extensions.repeatOnStarted
import com.example.kusithms_hdmedi_project.viewmodel.ReviewViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import java.text.DecimalFormat


class ReviewStep2Fragment : Fragment() {
    private var _binding : FragmentReviewStep2Binding? = null
    private val binding get() = _binding!!
    val viewmodel by activityViewModels<ReviewViewModel>()

    var strAmount = ""
    private val selectDiagnosisCategory = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentReviewStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFlexBox()

        binding.ratingbar.setOnRatingChangeListener { ratingBar, rating, _ ->
            viewmodel.writeReviewBody.update { viewmodel.writeReviewBody.value.copy(rating = rating.toInt()) }
        }

        binding.etDocName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewmodel.writeReviewBody.update { viewmodel.writeReviewBody.value.copy(doctor = p0.toString()) }
            }
        })

        binding.etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!TextUtils.isEmpty(p0.toString()) && p0.toString() != strAmount) {
                    strAmount = makeStringComma(p0.toString().replace(",", ""))!!
                    binding.etPrice.setText(strAmount)
                    Selection.setSelection(binding.etPrice.text, strAmount.length)

                    viewmodel.writeReviewBody.update { viewmodel.writeReviewBody.value.copy(price = p0.toString().replace(",","").toInt()) }
                }
            }
        })

        binding.ivClearName.setOnClickListener {
            binding.etDocName.setText("")
            viewmodel.writeReviewBody.update { viewmodel.writeReviewBody.value.copy(doctor = "") }
        }
        binding.ivClearPrice.setOnClickListener {
            binding.etPrice.setText("")
            viewmodel.writeReviewBody.update { viewmodel.writeReviewBody.value.copy(price = 0) }
            strAmount = ""
        }

    }

    private fun setFlexBox() {
        FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            binding.rvDiagnosisCategory.layoutManager = it
            binding.rvDiagnosisCategory.adapter = flexBoxAdapter
        }
    }

    private val flexBoxAdapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var list = listOf(DIAGNOSIS_TYPE.CAT.value, DIAGNOSIS_TYPE.FULL_BATTERY.value, DIAGNOSIS_TYPE.EEG.value, DIAGNOSIS_TYPE.COUNSEL.value, DIAGNOSIS_TYPE.MEDICINE.value, DIAGNOSIS_TYPE.ETC.value)
        var enumlist = listOf(DIAGNOSIS_TYPE.CAT.name, DIAGNOSIS_TYPE.FULL_BATTERY.name, DIAGNOSIS_TYPE.EEG.name, DIAGNOSIS_TYPE.COUNSEL.name, DIAGNOSIS_TYPE.MEDICINE.name, DIAGNOSIS_TYPE.ETC.name)
        val touchStatus = MutableList(list.size) { false }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_diagnosis_category, parent, false)
            return object : RecyclerView.ViewHolder(itemView) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val tvCategory = holder.itemView.findViewById<TextView>(R.id.tv_diagnosis_category)
            val ivChecked = holder.itemView.findViewById<ImageView>(R.id.iv_diagnosis_category)
            val llTop = holder.itemView.findViewById<LinearLayoutCompat>(R.id.ll_diagnosis_category)

            tvCategory.text = list[position]

            holder.itemView.setOnClickListener {
                if (touchStatus[position]) {
                    selectDiagnosisCategory.remove(enumlist[position])
                    viewmodel.writeReviewBody.update { viewmodel.writeReviewBody.value.copy(examinations = selectDiagnosisCategory) }
                    touchStatus[position] = false

                    tvCategory.setTextColor(ContextCompat.getColor(requireContext(), R.color.Tabunselected))
                    llTop.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.category_bg))
                    ivChecked.setImageResource(R.drawable.ic_check_off)
                } else {
                    selectDiagnosisCategory.add(enumlist[position])
                    viewmodel.writeReviewBody.update { viewmodel.writeReviewBody.value.copy(examinations = selectDiagnosisCategory) }
                    touchStatus[position] = true

                    when (position) {
                        0, 1, 2 -> {
                            tvCategory.setTextColor(ContextCompat.getColor(requireContext(), R.color.category_green_text))
                            llTop.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.category_green_bg))
                            ivChecked.setImageResource(R.drawable.ic_close_green)
                        }
                        3, 4 -> {
                            tvCategory.setTextColor(ContextCompat.getColor(requireContext(), R.color.category_red_text))
                            llTop.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.category_red_bg))
                            ivChecked.setImageResource(R.drawable.ic_colose_red)
                        }
                        5 -> {
                            tvCategory.setTextColor(ContextCompat.getColor(requireContext(), R.color.category_blue_text))
                            llTop.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.category_blue_bg))
                            ivChecked.setImageResource(R.drawable.ic_close_blue)
                        }
                    }
                }
            }
        }
        override fun getItemCount() = list.size
    }

    enum class DIAGNOSIS_TYPE(val value : String) {
        CAT("CAT 검사"),
        FULL_BATTERY("풀배터리 검사"),
        EEG("정량 뇌파 검사"),
        COUNSEL("상담"),
        MEDICINE("약 처방"),
        ETC("기타")
    }

    private fun makeStringComma(str: String): String? {    // 천단위 콤마설정.
        if (str.isEmpty()) {
            return ""
        }
        val value = str.toLong()
        val format = DecimalFormat("###,###")
        return format.format(value)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}