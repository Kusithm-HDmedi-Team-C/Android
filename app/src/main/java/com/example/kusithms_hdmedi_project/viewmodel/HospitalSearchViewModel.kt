package com.example.kusithms_hdmedi_project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kusithms_hdmedi_project.api.ApiService
import com.example.kusithms_hdmedi_project.api.RetrofitInstance
import com.example.kusithms_hdmedi_project.model.ApiResponseDetail
import com.example.kusithms_hdmedi_project.model.Hospital
import com.example.kusithms_hdmedi_project.model.HospitalApiResponse
import com.example.kusithms_hdmedi_project.model.HospitalDetailData
import com.example.kusithms_hdmedi_project.model.HospitalListResponse
import com.example.kusithms_hdmedi_project.model.Hospitals
import com.example.kusithms_hdmedi_project.model.Review
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
    //private var current =0

    //focus여부 전환 함수
    fun changefocus() {
        _isFocus.value = !(isFocus.value ?: false)
    }



    //받은 병원데이터 관리(전체 조회용)
    //private val _hospitalData = MutableLiveData<List<Hospital>>()
    private val _hospitalData = MutableLiveData<List<Hospitals>>()
    val hospitalData : LiveData<List<Hospitals>> = _hospitalData

    fun setnulldata()
    {
        _hospitalData.value= emptyList()
    }
    var hasNextPage = true

    //이름 검색시 해당 item만 보이게하기
    private val _nameofHospitalData = MutableLiveData<List<Hospitals>>()
    val nameofHospitalData:LiveData<List<Hospitals>> = _nameofHospitalData

    //검색 후
    private val _isresultOfSearch = MutableLiveData<Boolean>()
    val isresultOfSearch : LiveData<Boolean>
        get() = _isresultOfSearch

    //병원 상세 조회용
    private val _detailHospital = MutableLiveData<List<HospitalDetailData>>()
    val detailHospital : LiveData<List<HospitalDetailData>> = _detailHospital

    //리뷰 박스 visible용
//    private val _reviewClick = MutableLiveData<Boolean>()
//    val reviewClick : LiveData<Boolean> = _reviewClick


    //병원 리뷰 저장용
    private val _reviewData = MutableLiveData<List<Review>>()
    val reviewData:LiveData<List<Review>> = _reviewData

    //리뷰 visible용
    fun reviewVisibility(index:Int)
    {
        val current = _reviewData.value ?: return
        if(index in current.indices)
        {
            val review =current[index]
            review.isVisible = !review.isVisible
            _reviewData.value = current
        }

    }

    fun changeSearchState() {
        _isresultOfSearch.value = !(isresultOfSearch.value ?: false)
    }



    init{
        _isFocus.postValue(false)
        _isresultOfSearch.postValue(false)
        //_reviewClick.postValue(false)
    }

//    fun updateReviewClick()
//    {
//        _reviewClick.value = !(reviewClick.value ?: false)
//    }
    var currentPage:Int = 0

    //api요청 보내기
    fun getHospitalApiResponse(searchType:String, current:Int)
    {
        if(hasNextPage == false) return
        val call: Call<HospitalApiResponse> = apiService.getHospitalApiResponse(searchType,current)
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
                    currentPage+=1
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
        val call : Call<HospitalApiResponse> = apiService.searchHospitals(text)
        call.enqueue(object : Callback<HospitalApiResponse>{
            override fun onResponse(
                call: Call<HospitalApiResponse>,
                response: Response<HospitalApiResponse>
            ) {
                if(response.isSuccessful){
                    val hospitalListResponse = response.body()
                    val hospitals=hospitalListResponse?.data?.hospitals ?: emptyList()
                    _nameofHospitalData.postValue(hospitals)
                    Log.e("get", "${nameofHospitalData.value}")
                }
                else{
                    Log.e("fail","api 요청에 실패했습니다")
                }
            }

            override fun onFailure(call: Call<HospitalApiResponse>, t: Throwable) {
                Log.e("fail","api 요청에 실패했습니다2")
            }
        })

    }

    fun getHospitalDetail(id:Int)
    {
        apiService.getHospitalDetails(id).enqueue(object :Callback<ApiResponseDetail>{
            override fun onResponse(
                call: Call<ApiResponseDetail>,
                response: Response<ApiResponseDetail>
            ) {
                if(response.isSuccessful)
                {
                    val hospitalDetailResponse = response.body()

                    val details = hospitalDetailResponse?.data
                    val detailList = details?.let {listOf(it)} ?: emptyList()

                    val reviews = hospitalDetailResponse?.data?.reviews ?: emptyList()

                    _detailHospital.postValue(detailList)
                    _reviewData.postValue(reviews)
                }
                else{
                    Log.e("fail","api 요청에 실패했습니다")
                }
            }

            override fun onFailure(call: Call<ApiResponseDetail>, t: Throwable) {
                Log.e("fail","failure")
            }
        })
    }

}
