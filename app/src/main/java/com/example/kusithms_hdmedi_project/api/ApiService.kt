package com.example.kusithms_hdmedi_project.api

import com.example.kusithms_hdmedi_project.model.ApiResponse
import com.example.kusithms_hdmedi_project.model.HospitalApiResponse
import com.example.kusithms_hdmedi_project.model.RequestBodyModel
import com.example.kusithms_hdmedi_project.model.HospitalListResponse
import com.example.kusithms_hdmedi_project.model.WriteReviewBody
import com.example.kusithms_hdmedi_project.model.WriteReviewResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface ApiService {
    @POST("/api/v1/survey/result")
    fun postData(@Body requestBody: RequestBodyModel): Call<ApiResponse>

//    // 리뷰 작성
//    @POST("/api/v1/review")
//    suspend fun postReview(@Body body : WriteReviewBody) : Response<WriteReviewResponse>
    // 리뷰 작성
    @Multipart
    @POST("/api/v1/review")
    suspend fun postReview(
        @Part("createReviewRequestDto") createReviewRequestDto: RequestBody,
        @Part file: MultipartBody.Part?
    ) : Response<WriteReviewResponse>

    // 이름으로 병원 조회
    @GET("/api/v1/hospital/search")
    suspend fun searchHospitalsFromName(
        @Query("hospitalName") hospitalName: String
    ): Response<HospitalListResponse>

    // PostData랑 똑같은데 Response 반환
    @POST("/api/v1/survey/result")
    suspend fun postDataToResponse(@Body requestBody: RequestBodyModel): Response<ApiResponse>

    //전체 주소 조회
    @GET("/api/v1/hospital?")
    fun getHospitalApiResponse(@Query("pageNumber") value:Int): Call<HospitalApiResponse>


}