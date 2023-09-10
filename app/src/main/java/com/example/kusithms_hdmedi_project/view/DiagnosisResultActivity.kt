package com.example.kusithms_hdmedi_project.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.api.RequestBodyModel
import com.example.kusithms_hdmedi_project.databinding.ActivityDiagnosisBinding
import com.example.kusithms_hdmedi_project.databinding.ActivityDiagnosisResultBinding
import com.google.android.material.tabs.TabLayoutMediator

class DiagnosisResultActivity : AppCompatActivity() {
    private var _binding : ActivityDiagnosisResultBinding? = null
    private val binding get() = _binding!!

    // 진단결과 받아올 바디
    private lateinit var requestBody : RequestBodyModel
    //private var requestBody : RequestBodyModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestBody = intent.getSerializableExtra("answer_list") as RequestBodyModel
        //null check위헤서
//        val serializable = intent.getSerializableExtra("answer_list")
//        if(serializable != null)
//        {
//            requestBody = serializable as RequestBodyModel
//        }else{
//            println("진단값이 없습니다")
//            finish()
//        }

        _binding = ActivityDiagnosisResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
    }

    private fun initViewPager()
    {//adapter setting
        if(requestBody != null)
        {
            var viewPager2Adapter = ViewPager2Adapter(this)
            viewPager2Adapter.addFragment(ResultTabAFragment.newInstance(requestBody!!))
            viewPager2Adapter.addFragment(ResultTabBFragment())

            binding.viewpager.apply{
                adapter = viewPager2Adapter

                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                    }
                })
            }
            TabLayoutMediator(binding.tablayout, binding.viewpager){
                    tab, position->
                when(position){
                    //String helloString = getResources().getString(R.string.hello_string);
                    0 -> tab.text = getResources().getString(R.string.diagnosisResult)
                    1 -> tab.text = getResources().getString(R.string.recommend)
                }
            }.attach()
        }
        else{
            finish()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}