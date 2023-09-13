package com.example.kusithms_hdmedi_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kusithms_hdmedi_project.model.QuestionResponse
import com.example.kusithms_hdmedi_project.model.WriteReviewBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReviewViewModel: ViewModel() {
    val writeReviewBody = MutableStateFlow<WriteReviewBody>(WriteReviewBody())   // 작성하고 있는 전체 리뷰 내용 유지



}