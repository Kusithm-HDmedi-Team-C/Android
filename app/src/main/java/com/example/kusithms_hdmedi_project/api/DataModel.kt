package com.example.kusithms_hdmedi_project.api

data class ApiResponse(
    val status: Int,
    val message: String,
    val data: ResponseData
)

data class ResponseData(
    val totalScore: Int,
    val carelessnessScore: Int,
    val impulsivityScore: Int
)
data class RequestBodyModel(
    val surveyResults:List<SurveyResult>
)
data class SurveyResult(
    val surveyId:Int,
    val score : Int
)
