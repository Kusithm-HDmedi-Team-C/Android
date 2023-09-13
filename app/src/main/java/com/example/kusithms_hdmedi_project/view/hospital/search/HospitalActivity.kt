package com.example.kusithms_hdmedi_project.view.hospital.search

import android.content.Intent
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityHospitalBinding
import com.example.kusithms_hdmedi_project.model.Hospitals
import com.example.kusithms_hdmedi_project.view.MainActivity
import com.example.kusithms_hdmedi_project.view.diagnosis.DiagnosisPrevActivity
import com.example.kusithms_hdmedi_project.viewmodel.HospitalSearchViewModel

class HospitalActivity : AppCompatActivity() {
    private var _binding : ActivityHospitalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HospitalSearchViewModel by viewModels()
    private lateinit var hospitalAdapter: HospitalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backbtn = binding.backbtn
        val searchBox = binding.searchbox

        backbtn.setOnClickListener{
            val intent = Intent(MainActivity(), DiagnosisPrevActivity::class.java)
            startActivity(intent)
        }

        //돋보기 마진 설정용
        val searchicon = ContextCompat.getDrawable(this, R.drawable.search)
        val insetimg = InsetDrawable(searchicon,0,16,14,15)
        searchBox.setCompoundDrawablesWithIntrinsicBounds(null,null,insetimg,null)

        val arrayList=ArrayList<String>()
        arrayList.add("만족도 순")
        arrayList.add("후기 많은 순")
        val spinner = binding.menu
        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayList)
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        //리싸이클러뷰 초기화
        val recyclerView = binding.hospitalList
        var current:Int = 0

        //api호출
        viewModel.getHospitalApiResponse(current)

        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastItem = layoutManager.findLastVisibleItemPosition()
                if(viewModel.hasNextPage == true && lastItem == totalItemCount-1)
                {
                    current +=1
                    viewModel.getHospitalApiResponse(current)

                }
            }
        })
        viewModel.hospitalData.observe(this, {sortedMap->
            val combinedList = mutableListOf<Hospitals>()
            for(list in sortedMap.values){
                combinedList.addAll(list)
            }
            hospitalAdapter = HospitalAdapter(combinedList)
            recyclerView.adapter = hospitalAdapter
        })

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}