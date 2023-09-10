package com.example.kusithms_hdmedi_project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kusithms_hdmedi_project.api.ApiResponse
import com.example.kusithms_hdmedi_project.api.ApiService
import com.example.kusithms_hdmedi_project.api.RequestBodyModel
import com.example.kusithms_hdmedi_project.api.RetrofitInstance
import com.example.kusithms_hdmedi_project.api.SurveyResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class DiagnosisResultViewModel:ViewModel() {
    val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    //api응답을 저장하기 위해
    private val _apiResponse = MutableLiveData<ApiResponse>()
    val apiResponse : LiveData<ApiResponse>
        get()=_apiResponse

    //totalscore저장하기
    private val _totalscore = MutableLiveData<Int>()

    val totalscore : LiveData<Int>
        get() = _totalscore

    //totalscore저장하기
    private val _careless = MutableLiveData<Int>()

    val careless : LiveData<Int>
        get() = _careless

    val _impulsitive = MutableLiveData<Int>()
    val impulsitive : LiveData<Int>
        get() = _impulsitive

    fun updatetotal(totalscore : Int)
    {
        _totalscore.postValue(totalscore)
    }

    fun updateCareless(carelessnessScore:Int)
    {
        _careless.postValue(carelessnessScore)
    }

    fun updateimpulsitive(impulsivityScore:Int)
    {
        _impulsitive.postValue(impulsivityScore)
    }

    fun updateScoreFromApi(apiResponse: ApiResponse)
    {
        val totalScore = apiResponse?.data?.totalScore
        val carelessnessScore = apiResponse?.data?.carelessnessScore
        val impulsivityScore = apiResponse?.data?.impulsivityScore
        if(totalScore != null)
        {
            CoroutineScope(Dispatchers.IO).launch {
                updatetotal(totalScore)
                updateCareless(carelessnessScore!!)
                updateimpulsitive(impulsivityScore!!)
            }
        }
    }
    fun postDataApi(requestBodyModel: RequestBodyModel) = viewModelScope.launch {
        try{
            val requestBody = requestBodyModel
//            val requestBody = RequestBodyModel(
//                surveyResults = listOf(
//                    SurveyResult(surveyId = 1, score=2 ),
//                    SurveyResult(surveyId = 2, score=3 ),
//                    SurveyResult(surveyId = 3, score=2 ),
//                    SurveyResult(surveyId = 4, score=3 ),
//                )
//            )
            val call = apiService.postData(requestBody)
            call.enqueue(object : retrofit2.Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if(response.isSuccessful){
                        val apiResponse = response.body()
                        if(apiResponse != null){
                            _apiResponse.postValue(apiResponse!!)
                            updateScoreFromApi(apiResponse)
                            println("${apiResponse.data.totalScore}")
                        }
                        else{
                            println("api body is null")
                        }
                    }else{
                        println("api not successful")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    println("api통신 실패 ${t.message}")
                }
            })
        }catch (e: Exception){
            println("통신 실패 ${e.message}")
        }
    }


}