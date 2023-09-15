package com.example.kusithms_hdmedi_project.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.api.RetrofitInstance.service
import com.example.kusithms_hdmedi_project.model.HospitalData
import com.example.kusithms_hdmedi_project.model.WriteReviewBody
import com.example.kusithms_hdmedi_project.model.WriteReviewResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import org.json.JSONObject

class ReviewViewModel: ViewModel() {
    val step1Status = MutableLiveData<Boolean>()

    val writeReviewBody = MutableStateFlow<WriteReviewBody>(WriteReviewBody())   // 작성하고 있는 전체 리뷰 내용 유지
    val writeReviewResponse = MutableLiveData<Int>()

    private val _searchedHospitalList = MutableStateFlow<HospitalData>(HospitalData())
    val searchedHospitalList get() = _searchedHospitalList.asStateFlow()    // 이름으로 검색된 병원 목록

    fun searchHospitalsFromName(keyword: String) {
        viewModelScope.launch {
            val response = service.searchHospitalsFromName(keyword)
            _searchedHospitalList.update { response.body()?.data!! }
        }
    }

    fun postReview(bitmap: Bitmap) {
        viewModelScope.launch {
            val hospitalId = writeReviewBody.value.hospitalId
            val rating = writeReviewBody.value.rating
            val content = writeReviewBody.value.contents
            val doctor = writeReviewBody.value.doctor
            val price = writeReviewBody.value.price
            val examinations = writeReviewBody.value.examinations

            val examinationsList = examinations.map { "\"$it\"" }

            val jsonObject = JSONObject("{\"hospitalId\":$hospitalId,\"rating\":$rating,\"content\":\"${content}\", \"doctor\":\"${doctor}\",\"price\":$price,\"examinations\":$examinationsList}").toString()
            val jsonBody = RequestBody.create("application/json".toMediaTypeOrNull(),jsonObject)

            val file = RequestBody.create(MultipartBody.FORM, "")
            val body = MultipartBody.Part.createFormData("file","", file)

            val bitmapRequestBody = BitmapRequestBody(bitmap)
            val bitmapMultipartBody: MultipartBody.Part? =
                if (bitmapRequestBody == null) null
                else MultipartBody.Part.createFormData("file", "file", bitmapRequestBody)

            val response = service.postReview(jsonBody, bitmapMultipartBody)

            writeReviewResponse.postValue(response.body()?.status!!)
        }
    }

    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
        }
    }

    private fun String?.toPlainRequestBody() = requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())
}