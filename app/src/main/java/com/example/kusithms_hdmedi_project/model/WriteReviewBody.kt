package com.example.kusithms_hdmedi_project.model

data class WriteReviewBody(
    val hospitalId: Int,
    val rating: Int,
    val content: String,
    val doctor: String,
    val price: Int,
    val examinations: String
)