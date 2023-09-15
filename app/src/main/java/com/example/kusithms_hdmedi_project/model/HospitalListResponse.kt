package com.example.kusithms_hdmedi_project.model

data class HospitalListResponse(
    val data : HospitalData = HospitalData(),
    val message: String = "",
    val status: Int = 0
)

data class HospitalData(
    val count: Int = 0,
    val hasNextPage: Boolean = false,
    val hospitals: List<Hospital> = emptyList(),
    val pageNumber: Int = 0
)

data class Hospital(
    val address: String = "",
    val hospitalId: Int = 0,
    val name: String = "",
    val averageRating: Double = 0.0,
    val numberOfReview: Int = 0
)