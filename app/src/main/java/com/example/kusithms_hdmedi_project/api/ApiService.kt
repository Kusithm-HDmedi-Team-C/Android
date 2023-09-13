package com.example.kusithms_hdmedi_project.api

import com.example.kusithms_hdmedi_project.model.WriteReviewBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/v1/survey/result")
    fun postData(@Body requestBody: RequestBodyModel): Call<ApiResponse>

    // 리뷰 작성
    @POST("/api/v1/review")
    suspend fun postReview(@Body body : WriteReviewBody) : Response<Any>
}