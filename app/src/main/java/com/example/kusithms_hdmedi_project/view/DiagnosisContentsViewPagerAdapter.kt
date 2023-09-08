package com.example.kusithms_hdmedi_project.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.FragmentDiagnosisContentsBinding
import com.example.kusithms_hdmedi_project.model.QuestionResponse

class DiagnosisContentsViewPagerAdapter(
    private val activity: Activity,
    private val questionList: List<QuestionResponse>
): RecyclerView.Adapter<DiagnosisContentsViewPagerAdapter.PagerViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    inner class PagerViewHolder(private val binding: FragmentDiagnosisContentsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuestionResponse) {
            binding.response = item

            if (position == questionList.size - 1) {
                binding.tvFinish.visibility = View.VISIBLE

                binding.tvFinish.setOnClickListener {
                    finishDiagnosis()
                }
            }

            binding.llAnswer1.setOnClickListener {
                resetAnswerBoxUi()
                selectedAnswerBoxUi(binding.llAnswer1, binding.ivCheck1, binding.tvAnswer1)

                itemClickListener.onClickAnswer1(it, position)
            }
            binding.llAnswer2.setOnClickListener {
                resetAnswerBoxUi()
                selectedAnswerBoxUi(binding.llAnswer2, binding.ivCheck2, binding.tvAnswer2)

                itemClickListener.onClickAnswer2(it, position)
            }
            binding.llAnswer3.setOnClickListener {
                resetAnswerBoxUi()
                selectedAnswerBoxUi(binding.llAnswer3, binding.ivCheck3, binding.tvAnswer3)

                itemClickListener.onClickAnswer3(it, position)
            }
            binding.llAnswer4.setOnClickListener {
                resetAnswerBoxUi()
                selectedAnswerBoxUi(binding.llAnswer4, binding.ivCheck4, binding.tvAnswer4)

                itemClickListener.onClickAnswer4(it, position)
            }

        }

        /** 체크된 답변 UI상태 초기화하는 함수 **/
        fun resetAnswerBoxUi() {
            binding.llAnswer1.setBackgroundResource(R.drawable.bg_diagnosis_question_check_off)
            binding.ivCheck1.setImageResource(R.drawable.ic_check_off)
            binding.tvAnswer1.setTextColor(Color.parseColor("#8D94A0"))
            binding.llAnswer2.setBackgroundResource(R.drawable.bg_diagnosis_question_check_off)
            binding.ivCheck2.setImageResource(R.drawable.ic_check_off)
            binding.tvAnswer2.setTextColor(Color.parseColor("#8D94A0"))
            binding.llAnswer3.setBackgroundResource(R.drawable.bg_diagnosis_question_check_off)
            binding.ivCheck3.setImageResource(R.drawable.ic_check_off)
            binding.tvAnswer3.setTextColor(Color.parseColor("#8D94A0"))
            binding.llAnswer4.setBackgroundResource(R.drawable.bg_diagnosis_question_check_off)
            binding.ivCheck4.setImageResource(R.drawable.ic_check_off)
            binding.tvAnswer4.setTextColor(Color.parseColor("#8D94A0"))
        }
    }

    private fun selectedAnswerBoxUi(
        llTop: View,
        ivCheck: ImageView,
        tvQuestion: TextView
    ) {
        llTop.setBackgroundResource(R.drawable.bg_diagnosis_question_check_on)
        ivCheck.setImageResource(R.drawable.ic_check_on)
        tvQuestion.setTextColor(Color.parseColor("#4E7FFF"))
    }

    fun finishDiagnosis() {
        activity.startActivity(Intent(activity, DiagnosisResultActivity::class.java))
        activity.finish()
    }

    interface OnItemClickListener {
        fun onClickAnswer1(v: View, position: Int)
        fun onClickAnswer2(v: View, position: Int)
        fun onClickAnswer3(v: View, position: Int)
        fun onClickAnswer4(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = FragmentDiagnosisContentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiagnosisContentsViewPagerAdapter.PagerViewHolder, position: Int) {
        holder.bind(questionList[position])
    }

    override fun getItemCount() = questionList.size
}