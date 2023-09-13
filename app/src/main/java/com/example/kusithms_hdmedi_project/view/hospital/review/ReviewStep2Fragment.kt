package com.example.kusithms_hdmedi_project.view.hospital.review

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.FragmentReviewStep2Binding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import java.text.DecimalFormat


class ReviewStep2Fragment : Fragment() {
    private var _binding : FragmentReviewStep2Binding? = null
    private val binding get() = _binding!!
    var strAmount = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentReviewStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFlexBox()

        binding.ratingbar.setOnRatingBarChangeListener { ratingBar, fl, b ->

        }

        binding.etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!TextUtils.isEmpty(p0.toString()) && p0.toString() != strAmount) {
                    strAmount = makeStringComma(p0.toString().replace(",", ""))!!
                    binding.etPrice.setText(strAmount)
                    val editable = binding.etPrice.text
                    Selection.setSelection(editable, strAmount.length)
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.ivClearName.setOnClickListener {
            binding.etDocName.setText("")
        }

        binding.ivClearPrice.setOnClickListener {
            binding.etPrice.setText("")
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
        var list = listOf("CAT 검사", "풀배터리 검사", "정량 뇌파 검사", "상담", "약 처방", "기타")
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
                    touchStatus[position] = false

                    tvCategory.setTextColor(ContextCompat.getColor(requireContext(), R.color.Tabunselected))
                    llTop.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.category_bg))
                    ivChecked.setImageResource(R.drawable.ic_check_off)
                } else {
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