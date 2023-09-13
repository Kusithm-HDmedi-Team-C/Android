package com.example.kusithms_hdmedi_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kusithms_hdmedi_project.model.QuestionResponse
import com.example.kusithms_hdmedi_project.model.WriteReviewBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReviewViewModel: ViewModel() {
    private val _step1CompleteStatus = MutableStateFlow<Boolean>(false)
    val step1CompleteStatus get() =  _step1CompleteStatus.asStateFlow()    // 리뷰 작성 1번째 단계

    private val _step2CompleteStatus = MutableStateFlow<Boolean>(false)
    val step2CompleteStatus get() =  _step2CompleteStatus.asStateFlow()    // 리뷰 작성 2번째 단계

    val reviewContents = MutableLiveData<WriteReviewBody>()    // 리뷰 작성하고 있는 내용 유지

    fun step1Complete(status: Boolean) {
        _step1CompleteStatus.update { status }
    }
}