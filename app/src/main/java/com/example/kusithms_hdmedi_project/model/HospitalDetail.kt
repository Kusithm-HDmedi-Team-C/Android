package com.example.kusithms_hdmedi_project.model

import com.google.gson.annotations.SerializedName


data class ApiResponseDetail(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: HospitalDetailData
)

data class HospitalDetailData(
    @SerializedName("hospitalId") val hospitalId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("averageRating") val averageRating: Double,
    @SerializedName("address") val address: String,
    @SerializedName("url") val url: String,
    @SerializedName("mapUrl") val mapUrl: String,
    @SerializedName("telephone") val telephone:String,
    @SerializedName("reviews") val reviews: List<Review>,
    @SerializedName("pageNumber") val pageNumber: Int,
    @SerializedName("count") val count: Int,
    @SerializedName("hasNextPage") val hasNextPage: Boolean
)
data class Review(
    @SerializedName("reviewId") val reviewId: Int,
    @SerializedName("rating") val rating: Int,
    @SerializedName("content") val content: String,
    @SerializedName("doctor") val doctor: String,
    @SerializedName("price") val price: Int,
    @SerializedName("examinations") val examinations: List<String>,
    @SerializedName("createdAt") val createdAt: String
)

