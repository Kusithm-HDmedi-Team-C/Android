package com.example.kusithms_hdmedi_project.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/v1/survey/result")
    fun postData(@Body requestBody: RequestBodyModel): Call<ApiResponse>


}