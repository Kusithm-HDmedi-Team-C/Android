package com.example.kusithms_hdmedi_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kusithms_hdmedi_project.model.QuestionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DiagnosisViewModel: ViewModel() {
    private val _questionList = MutableStateFlow<List<QuestionResponse>>(emptyList())
    val questionList = _questionList.asStateFlow()    // 전체 문항들

    val pbStepSize = MutableLiveData<Int>(0)

    fun refreshPb(nowPage: Int) {
        val oneStep = 100/questionList.value.size
        pbStepSize.value = oneStep * nowPage
    }

    fun getQuestionList() {
        val testList = mutableListOf<QuestionResponse>()
        testList.add(QuestionResponse(1, "첫번째 문항", "샘플 1111"))
        testList.add(QuestionResponse(2, "두번째 문항", "샘플 2"))
        testList.add(QuestionResponse(3, "세번째 문항", "샘플 33"))
        testList.add(QuestionResponse(4, "네번째 문항", "샘플 44444444444"))
        testList.add(QuestionResponse(5, "다섯번째 문항", "샘플5"))

        _questionList.update { testList }
    }
}