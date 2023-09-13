package com.example.kusithms_hdmedi_project.api

import com.example.kusithms_hdmedi_project.model.ApiResponse
import com.example.kusithms_hdmedi_project.model.HospitalApiResponse
import com.example.kusithms_hdmedi_project.model.RequestBodyModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("/api/v1/survey/result")
    fun postData(@Body requestBody: RequestBodyModel): Call<ApiResponse>

    // PostData랑 똑같은데 Response 반환
    @POST("/api/v1/survey/result")
    suspend fun postDataToResponse(@Body requestBody: RequestBodyModel): Response<ApiResponse>

    //전체 주소 조회
    @GET("/api/v1/hospital?")
    fun getHospitalApiResponse(@Query("pageNumber") value:Int): Call<HospitalApiResponse>


}