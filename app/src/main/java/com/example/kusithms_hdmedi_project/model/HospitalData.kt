package com.example.kusithms_hdmedi_project.model

data class Hospitals(
    val hospitalId:Int,
    val name:String,
    val averageRating:Double,
    val numberOfReviews:Int,
    val address:String
)

data class HospitalApiResponse(
    val status:Int,
    val message:String,
    val data:Data
)
data class Data(
    val hospitals:List<Hospitals>,
    val pageNumber:Int,
    val count:Int,
    val hasNextPage:Boolean
)