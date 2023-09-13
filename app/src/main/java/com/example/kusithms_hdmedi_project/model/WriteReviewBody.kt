package com.example.kusithms_hdmedi_project.model

data class WriteReviewBody(
    val hospitalId: Int  = 0,
    val rating: Int = 0,
    var contents: String = "",
    val doctor: String = "",
    val price: Int = 0,
    var examinations: MutableList<String> = mutableListOf<String>()
)