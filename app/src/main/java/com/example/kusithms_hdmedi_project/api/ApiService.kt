package com.example.kusithms_hdmedi_project.api

import com.example.kusithms_hdmedi_project.model.HospitalListResponse
import com.example.kusithms_hdmedi_project.model.WriteReviewBody
import com.example.kusithms_hdmedi_project.model.WriteReviewResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/api/v1/survey/result")
    fun postData(@Body requestBody: RequestBodyModel): Call<ApiResponse>

    // 리뷰 작성
    @POST("/api/v1/review")
    suspend fun postReview(@Body body : WriteReviewBody) : Response<WriteReviewResponse>

    // 이름으로 병원 조회
    @GET("/api/v1/hospital/search")
    suspend fun searchHospitalsFromName(
        @Query("hospitalName") hospitalName: String
    ): Response<HospitalListResponse>

    // PostData랑 똑같은데 Response 반환
    @POST("/api/v1/survey/result")
    suspend fun postDataToResponse(@Body requestBody: RequestBodyModel): Response<ApiResponse>
}