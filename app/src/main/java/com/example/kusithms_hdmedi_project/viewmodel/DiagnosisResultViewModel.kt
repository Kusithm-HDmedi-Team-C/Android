package com.example.kusithms_hdmedi_project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kusithms_hdmedi_project.model.ApiResponse
import com.example.kusithms_hdmedi_project.api.ApiService
import com.example.kusithms_hdmedi_project.model.RequestBodyModel
import com.example.kusithms_hdmedi_project.api.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class DiagnosisResultViewModel:ViewModel() {
    val location=IntArray(2)
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

    val _iswarning = MutableLiveData<Boolean>()
    val iswarning:LiveData<Boolean>
        get()=_iswarning
    val _isclose1=MutableLiveData<Boolean>()
    val isclose1 : LiveData<Boolean>
        get() = _isclose1
    val _isclose2=MutableLiveData<Boolean>()
    val isclose2 : LiveData<Boolean>
        get() = _isclose2
    val _isclose4=MutableLiveData<Boolean>()
    val isclose4 : LiveData<Boolean>
        get() = _isclose4
    val _isclose5=MutableLiveData<Boolean>()
    val isclose5 : LiveData<Boolean>
        get() = _isclose5
    val _isclose6 =MutableLiveData<Boolean>()
    val isclose6 : LiveData<Boolean>
        get() = _isclose6
    val _isclose7=MutableLiveData<Boolean>()
    val isclose7 : LiveData<Boolean>
        get() = _isclose7

    val _isclose8=MutableLiveData<Boolean>()
    val isclose8: LiveData<Boolean>
        get() = _isclose8
    init{
        _isclose1.postValue(true)
        _isclose2.postValue(true)
        _isclose4.postValue(true)
        _isclose5.postValue(true)
        _isclose6.postValue(true)
        _isclose7.postValue(true)
        _isclose8.postValue(true)

    }
    fun isclose1()
    {
        _isclose1.value = _isclose1.value?.let { !it } ?: false
    }
    fun isclose2()
    {
        _isclose2.value = _isclose2.value?.let { !it } ?: false
    }
    fun isclose4()
    {
        _isclose4.value = _isclose4.value?.let { !it } ?: false
    }
    fun isclose5()
    {
        _isclose5.value = _isclose5.value?.let { !it } ?: false
    }
    fun isclose6()
    {
        _isclose6.value = _isclose6.value?.let { !it } ?: false
    }
    fun isclose7()
    {
        _isclose7.value = _isclose7.value?.let { !it } ?: false
    }
    fun isclose8()
    {
        _isclose8.value = _isclose8.value?.let { !it } ?: false
    }

    fun iswarning(totalscore:Int)
    {
        if(totalscore >= 19)
        {
            _iswarning.postValue(true)
        }
        else
        {
            _iswarning.postValue(false)
        }
    }

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
            iswarning(totalScore)
        }
    }

    fun postDataApi(requestBodyModel: RequestBodyModel) = viewModelScope.launch {
        try{
            val requestBody = requestBodyModel

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

    fun postDiagnosisResult(apiResponse: ApiResponse) {
        _apiResponse.postValue(apiResponse)
        updateScoreFromApi(apiResponse)
    }


}