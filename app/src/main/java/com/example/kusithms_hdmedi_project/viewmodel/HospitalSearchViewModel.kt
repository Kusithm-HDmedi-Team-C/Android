package com.example.kusithms_hdmedi_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kusithms_hdmedi_project.api.ApiService
import com.example.kusithms_hdmedi_project.api.RetrofitInstance
import com.example.kusithms_hdmedi_project.model.Hospital
import com.example.kusithms_hdmedi_project.model.HospitalApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.SortedMap
import kotlin.properties.Delegates

class HospitalSearchViewModel: ViewModel() {
    val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    //받은 병원데이터 관리
    //private val _hospitalData = MutableLiveData<List<Hospital>>()
    private val _hospitalData = MutableLiveData<SortedMap<Int,List<Hospital>>>()
    val hospitalData : LiveData<SortedMap<Int,List<Hospital>>> = _hospitalData
    var hasNextPage = true


    //api요청 보내기
    fun getHospitalApiResponse(currentpage: Int)
    {
        if(hasNextPage == false) return
        val call: Call<HospitalApiResponse> = apiService.getHospitalApiResponse(currentpage)
        call.enqueue(object : Callback<HospitalApiResponse>{
            override fun onResponse(
                call: Call<HospitalApiResponse>,
                response: Response<HospitalApiResponse>
            ) {
                if(response.isSuccessful)
                {
                    val apiResponse = response.body()
                    hasNextPage = apiResponse?.data?.hasNextPage ?: false
                    val hospitals=apiResponse?.data?.hospitals ?: emptyList()

                    val currentHospitals = _hospitalData.value ?: sortedMapOf()
                    currentHospitals[currentpage] = hospitals
                    _hospitalData.postValue(currentHospitals)
                }
                else{
                    Log.e("fail","api 요청에 실패했습니다")
                }
            }

            override fun onFailure(call: Call<HospitalApiResponse>, t: Throwable) {
                Log.e("fail","네트워크 오류")
            }
        })
    }

}
