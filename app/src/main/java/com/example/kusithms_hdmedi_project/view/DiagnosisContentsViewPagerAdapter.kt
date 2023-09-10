package com.example.kusithms_hdmedi_project.view

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.api.ApiService
import com.example.kusithms_hdmedi_project.api.RequestBodyModel
import com.example.kusithms_hdmedi_project.api.RetrofitInstance
import com.example.kusithms_hdmedi_project.api.SurveyResult
import com.example.kusithms_hdmedi_project.databinding.FragmentDiagnosisContentsBinding
import com.example.kusithms_hdmedi_project.model.QuestionResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiagnosisContentsViewPagerAdapter(
    private val activity: Activity,
    private val questionList: List<QuestionResponse>
): RecyclerView.Adapter<DiagnosisContentsViewPagerAdapter.PagerViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private var answerList = Array<Int>(18) {-1}

    inner class PagerViewHolder(private val binding: FragmentDiagnosisContentsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuestionResponse) {
            binding.response = item

            resetAnswerBoxUi()
            when (answerList[adapterPosition]) {
                0 -> {
                    selectedAnswerBoxUi(binding.llAnswer1, binding.ivCheck1, binding.tvAnswer1)
                }
                1 -> {
                    selectedAnswerBoxUi(binding.llAnswer2, binding.ivCheck2, binding.tvAnswer2)
                }
                2 -> {
                    selectedAnswerBoxUi(binding.llAnswer3, binding.ivCheck3, binding.tvAnswer3)
                }
                3 -> {
                    selectedAnswerBoxUi(binding.llAnswer4, binding.ivCheck4, binding.tvAnswer4)
                }
            }

            if (adapterPosition == questionList.size - 1) {
                binding.tvFinish.visibility = View.VISIBLE

                if (answerList[adapterPosition] == -1) {
                    // 마지막 문항인데 아직 답변 선택 안한 경우

                } else {
                    binding.tvFinish.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.main_blue))

                    binding.tvFinish.setOnClickListener {
                        val list = mutableListOf<SurveyResult>()
                        answerList.forEachIndexed { index, i ->
                            list.add(SurveyResult(surveyId = index+1, score = i))
                        }
                        val requestBody = RequestBodyModel(list)

                        finishDiagnosis(requestBody)
                    }
                }


            } else {
                binding.tvFinish.visibility = View.INVISIBLE
            }

            binding.llAnswer1.setOnClickListener {
                resetAnswerBoxUi()
                selectedAnswerBoxUi(binding.llAnswer1, binding.ivCheck1, binding.tvAnswer1)

                itemClickListener.onClickAnswer(it, adapterPosition, 0)
            }
            binding.llAnswer2.setOnClickListener {
                resetAnswerBoxUi()
                selectedAnswerBoxUi(binding.llAnswer2, binding.ivCheck2, binding.tvAnswer2)

                itemClickListener.onClickAnswer(it, adapterPosition, 1)
            }
            binding.llAnswer3.setOnClickListener {
                resetAnswerBoxUi()
                selectedAnswerBoxUi(binding.llAnswer3, binding.ivCheck3, binding.tvAnswer3)

                itemClickListener.onClickAnswer(it, adapterPosition, 2)
            }
            binding.llAnswer4.setOnClickListener {
                resetAnswerBoxUi()
                selectedAnswerBoxUi(binding.llAnswer4, binding.ivCheck4, binding.tvAnswer4)

                itemClickListener.onClickAnswer(it, adapterPosition, 3)
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

    fun setAnswerList(newAnswerList: Array<Int>) {
        answerList = newAnswerList
        notifyDataSetChanged()
    }

    fun finishDiagnosis(requestBody: RequestBodyModel) {
        activity.startActivity(Intent(activity, DiagnosisResultActivity::class.java).apply {
            putExtra("answer_list", requestBody)
        })
        activity.finish()
    }

    interface OnItemClickListener {
        fun onClickAnswer(v: View, position: Int, answerNumber: Int)
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