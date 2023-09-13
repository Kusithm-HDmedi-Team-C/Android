package com.example.kusithms_hdmedi_project.viewmodel

import androidx.lifecycle.ViewModel
import com.example.kusithms_hdmedi_project.model.QuestionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReviewViewModel: ViewModel() {
    private val _step1CompleteStatus = MutableStateFlow<Boolean>(false)
    val step1CompleteStatus get() =  _step1CompleteStatus.asStateFlow()    // 리뷰 작성 1번째 단계

    private val _step2CompleteStatus = MutableStateFlow<Boolean>(false)
    val step2CompleteStatus get() =  _step2CompleteStatus.asStateFlow()    // 리뷰 작성 2번째 단계


    fun step1Complete(status: Boolean) {
        _step1CompleteStatus.update { status }
    }
}