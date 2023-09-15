package com.example.kusithms_hdmedi_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kusithms_hdmedi_project.api.ApiService
import com.example.kusithms_hdmedi_project.api.RetrofitInstance
import com.example.kusithms_hdmedi_project.model.Hospital
import com.example.kusithms_hdmedi_project.model.HospitalApiResponse
import com.example.kusithms_hdmedi_project.model.HospitalListResponse
import com.example.kusithms_hdmedi_project.model.Hospitals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.SortedMap
import kotlin.properties.Delegates

class HospitalSearchViewModel: ViewModel() {
    val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    val _isFocus=MutableLiveData<Boolean>()
    val isFocus :LiveData<Boolean>
        get() = _isFocus
    private var currentpage =1

    //focus여부 전환 함수
    fun changefocus() {
        _isFocus.value = !(isFocus.value ?: false)
    }



    //받은 병원데이터 관리(전체 조회용)
    //private val _hospitalData = MutableLiveData<List<Hospital>>()
    private val _hospitalData = MutableLiveData<List<Hospitals>>()
    val hospitalData : LiveData<List<Hospitals>> = _hospitalData
    var hasNextPage = true

    //이름 검색시 해당 item만 보이게하기
    private val _nameofHospitalData = MutableLiveData<List<Hospitals>>()
    val nameofHospitalData:LiveData<List<Hospitals>> = _nameofHospitalData

    //검색 후
    private val _isresultOfSearch = MutableLiveData<Boolean>()
    val isresultOfSearch : LiveData<Boolean>
        get() = _isresultOfSearch

    fun changeSearchState()
    {
        _isresultOfSearch.postValue(true)
    }



    init{
        _isFocus.postValue(false)
        _isresultOfSearch.postValue(false)
    }

    //api요청 보내기
    fun getHospitalApiResponse()
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
                    val newHospitals = response.body()?.data?.hospitals ?: emptyList()
                    val currentHospitals = _hospitalData.value ?: emptyList()
                    val combinedHospitals =  currentHospitals + newHospitals
                    _hospitalData.postValue(combinedHospitals)
                    currentpage+=1
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
    fun getHospitalFromName(text : String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val response=apiService.searchHospitals(text)
            if(response.isSuccessful){
                //val hospitals=apiResponse?.data?.hospitals ?: emptyList()
                val hospitalListResponse = response.body()
                val hospitals=hospitalListResponse?.data?.hospitals ?: emptyList()
                _nameofHospitalData.postValue(hospitals)
                changeSearchState()

            }
            else{
                Log.e("fail","api 요청에 실패했습니다")
            }
        }
    }

}
