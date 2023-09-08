package com.example.kusithms_hdmedi_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kusithms_hdmedi_project.model.QuestionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiagnosisViewModel: ViewModel() {
    private val _questionList = MutableStateFlow<List<QuestionResponse>>(emptyList())
    val questionList = _questionList.asStateFlow()    // 전체 문항들

    private val _answerList = MutableStateFlow<Array<Int>>(Array(18) { -1 })
    val answerList = _answerList.asStateFlow()    // 사용자가 문항에 답한 답변 리스트

    val pbStepSize = MutableLiveData<Int>(0)

    fun setAnswerList(index: Int, answerNumber: Int) {
        val prevAnswerList = _answerList.value.copyOf()
        prevAnswerList[index] = answerNumber
        _answerList.update { prevAnswerList }
    }

    fun refreshPb(nowPage: Int) {
        val oneStep = 100/questionList.value.size
        pbStepSize.value = oneStep * nowPage
    }

    fun getQuestionList() {
        val testList = mutableListOf<QuestionResponse>()
        testList.add(QuestionResponse(1, "세부적인 면에 대해 꼼꼼하게 주의를 기울이지 못하거나 학업에서 부주의한 실수를 한다.", "세부적인 것을 못 보고 넘어가거나 놓친다. "))
        testList.add(QuestionResponse(2, "손발을 가만히 두지 못하거나 의자에 앉아서도 몸을 꼼지락 거린다.", ""))
        testList.add(QuestionResponse(3, "일을 하거나 놀이를 할 때 지속적으로 주의를 집중하는데 어려움이 있다.", "수업시간에 딴 생각에 빠져 있는다."))
        testList.add(QuestionResponse(4, "네번째 문항", "샘플 44444444444"))
        testList.add(QuestionResponse(5, "다섯번째 문항", "샘플5"))

        _questionList.update { testList }
    }
}