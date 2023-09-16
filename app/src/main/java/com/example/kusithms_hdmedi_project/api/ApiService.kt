package com.example.kusithms_hdmedi_project.api

import com.example.kusithms_hdmedi_project.model.ApiResponse
import com.example.kusithms_hdmedi_project.model.ApiResponseDetail
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
    fun getHospitalApiResponse(@Query("searchType") searchType: String, @Query("pageNumber") value:Int): Call<HospitalApiResponse>

//    //만족도순 조회
//    @GET("/api/v1/hospital")
//    fun getHospitalDataRating(@Query("searchType") searchType: String = "averageRating", @Query("pageNumber") pageNumber: Int): Call<HospitalApiResponse>
//
//    //리뷰순 조회
//    @GET("/api/v1/hospital")
//    fun getHospitalDataReview(@Query("searchType") searchType: String = "numberOfReviews", @Query("pageNumber") pageNumber: Int): Call<HospitalApiResponse>


    //이름으로 주소조회(병원 검색창에서 평점및 기타 데이터들도 필요하기에)
    @GET("/api/v1/hospital/search?")
    suspend fun searchHospitals(
        @Query("hospitalName") hospitalName: String
    ): Response<HospitalApiResponse>

    //병원 상세 조회
    @GET("/api/v1/hospital/details/{id}?pageNumber=0&pageSize=10")
    fun getHospitalDetails(@Path("id") id:Int): Call<ApiResponseDetail>

}