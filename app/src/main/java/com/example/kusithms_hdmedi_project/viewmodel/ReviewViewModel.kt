package com.example.kusithms_hdmedi_project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kusithms_hdmedi_project.api.RetrofitInstance.service
import com.example.kusithms_hdmedi_project.model.HospitalData
import com.example.kusithms_hdmedi_project.model.WriteReviewBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReviewViewModel: ViewModel() {
    val writeReviewBody = MutableStateFlow<WriteReviewBody>(WriteReviewBody())   // 작성하고 있는 전체 리뷰 내용 유지

    private val _searchedHospitalList = MutableStateFlow<HospitalData>(HospitalData())
    val searchedHospitalList get() = _searchedHospitalList.asStateFlow()    // 이름으로 검색된 병원 목록

    fun postReview(body : WriteReviewBody) {
        viewModelScope.launch {
            val response = service.postReview(body)
        }
    }

    fun searchHospitalsFromName(keyword: String) {
        viewModelScope.launch {
            val response = service.searchHospitalsFromName(keyword)
            _searchedHospitalList.update { response.body()?.data!! }
        }
    }

}