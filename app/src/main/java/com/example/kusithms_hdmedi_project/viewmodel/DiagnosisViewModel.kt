package com.example.kusithms_hdmedi_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kusithms_hdmedi_project.api.ApiResponse
import com.example.kusithms_hdmedi_project.api.RequestBodyModel
import com.example.kusithms_hdmedi_project.api.RetrofitInstance.service
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

    val diagnosisResult = MutableLiveData<ApiResponse>()

    fun setAnswerList(index: Int, answerNumber: Int) {
        val prevAnswerList = _answerList.value.copyOf()
        prevAnswerList[index] = answerNumber
        _answerList.update { prevAnswerList }
    }

    fun refreshPb(nowPage: Int) {
        val oneStep = 90/questionList.value.size
        pbStepSize.value = oneStep * nowPage
    }

    fun getQuestionList() {
        val testList = mutableListOf<QuestionResponse>()
        testList.add(QuestionResponse(1, "세부적인 면에 대해 꼼꼼하게 주의를 기울이지 못하거나 학업에서 부주의한 실수를 한다.", "세부적인 것을 못 보고 넘어가거나 놓친다. "))
        testList.add(QuestionResponse(2, "손발을 가만히 두지 못하거나 의자에 앉아서도 몸을 꼼지락 거린다.", ""))
        testList.add(QuestionResponse(3, "일을 하거나 놀이를 할 때 지속적으로 주의를 집중하는데 어려움이 있다.", "수업시간에 딴 생각에 빠져 있는다."))
        testList.add(QuestionResponse(4, "자리에 앉아 있어야 하는 교실이나 다른 상황에서 앉아있지 못한다.", ""))
        testList.add(QuestionResponse(5, "다른 사람이 마주보고 이야기할 때 경청하지 않는 것처럼 보인다.", "명백하게 주의집중을 방해하는 것이 없는데 마음이 다른 곳에 있는 것처럼 보인다."))
        testList.add(QuestionResponse(6,"그렇게 하면 안되는 상황에서 지나치게 뛰어다니거나 기어오른다.","버스나 지하철, 식당 같은 장소에서 뛰어다닌다."))
        testList.add(QuestionResponse(7,"지시를 따르지 않고, 일을 끝내지 못한다.","책상 앞에 앉아있어도 공부한 것은 별로 없다. 과제를 시작하지만 빨리 주의를 잃고 쉽게 곁길로 샌다."))
        testList.add(QuestionResponse(8,"여가 활동이나 재미있는 일에 조용히 참여하기가 어렵다.",""))
        testList.add(QuestionResponse(9,"과제와 일을 체계적으로 하지 못한다.","순차적인 과제를 처리하는 데 어려움을 느낀다. 물건이나 소지품을 정리하는 데 어려움을 느낀다. 시간 관리를 잘 하지 못한다. 마감시간을 맞추지 못한다."))
        testList.add(QuestionResponse(10,"끊임없이 무엇인가를 하거나 마치 모터가 돌아가듯 움직인다.","음식점 같은 곳에서 장시간 동안 가만히 있을 수 없거나 불편해한다."))
        testList.add(QuestionResponse(11,"지속적이 노력이 요구되는 과제(학교 공부나 숙제)를 하지 않으려 한다.",""))
        testList.add(QuestionResponse(12,"지나치게 말을 많이 한다.",""))
        testList.add(QuestionResponse(13,"과제나 일을 하는데 필요한 물건들을 잃어버린다.","장난감, 연필 등을 잃어버린다."))
        testList.add(QuestionResponse(14,"질문이 채 끝나기도 전에 성급하게 대답한다.","다른 사람의 말을 가로챈다. 대화 시 자신의 차례를 기다리지 못한다."))
        testList.add(QuestionResponse(15,"쉽게 산만해 진다.",""))
        testList.add(QuestionResponse(16,"차례를 기다리는데 어려움이 있다.","줄을 서 있는 동안 기다림에 어려움을 겪는다."))
        testList.add(QuestionResponse(17,"일상적으로 하는 일을 잊어버린다.","숙제를 잊어버린다. 도시락을 두고 학교에 간다. 알림장 써오기를 까먹는다."))
        testList.add(QuestionResponse(18,"다른 사람을 방해하거나 간섭한다.","다른 사람의 대화나 게임, 활동에 참견한다. 다른 사람에게 묻거나 허락을 받지 않고 다른 사람의 물건을 사용한다."))

        _questionList.value = testList
    }

    fun postDataToResponse(requestBodyModel: RequestBodyModel) {
        viewModelScope.launch {
            val response = service.postDataToResponse(requestBodyModel)
            diagnosisResult.postValue(response.body())
        }
    }
}