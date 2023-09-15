package com.example.kusithms_hdmedi_project.view.hospital.search

import android.content.Intent
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityHospitalBinding
import com.example.kusithms_hdmedi_project.model.Hospital
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
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        val backbtn = binding.backbtn
        val searchbox = binding.searchbox

        backbtn.setOnClickListener{
            val intent = Intent(MainActivity(), DiagnosisPrevActivity::class.java)
            startActivity(intent)
        }
        //취소버튼 이전화면
        binding.cancelTxt.setOnClickListener(){
            val fragmentManager = supportFragmentManager
            val fragment = fragmentManager.findFragmentById(R.id.fragmentcontainer)
            if(fragment != null)
            {
                val transaction = fragmentManager.beginTransaction()
                transaction.remove(fragment)
                transaction.commit()
            }
        }

        //focus시 fragment로 이동
        searchbox.setOnFocusChangeListener(object:OnFocusChangeListener{
            override fun onFocusChange(view: View, hasFocus:Boolean)
            {
                if(hasFocus)
                {
                    Log.e("log","ggg")
                    supportFragmentManager.beginTransaction()
                        .add(binding.fragmentcontainer.id,SearchListFragment())
                        .addToBackStack(null)
                        .commit()
                    viewModel.changefocus()
                    Log.e("error","${viewModel.isFocus.value.toString()}")

                }
//                viewModel.changefocus()
//                Log.e("error","${viewModel.isFocus.value.toString()}")
            }
        })

        //edittext내부 지우기 버튼
        searchbox.setOnTouchListener(View.OnTouchListener{

            v,event->
            if(event.action == MotionEvent.ACTION_UP){
                val drawableRightXStart = searchbox.right - searchbox.totalPaddingRight
                if(event.rawX>=drawableRightXStart)
                {
                    searchbox.setText("")
                    return@OnTouchListener true
                }
            }
            false
        })

        searchbox.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.getHospitalFromName(p0.toString())
            }
        })



        val arrayList=ArrayList<String>()
        arrayList.add("만족도 순")
        arrayList.add("후기 많은 순")
        val spinner = binding.menu
        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown,arrayList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
        spinner.adapter = adapter
        spinner.post{
            spinner.dropDownHorizontalOffset = spinner.height
        }
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //val selectedItem = p0?.getItemAtPosition(p2).toString()
                when(position){
                     0 ->
                     {
                         //만족도 순 정렬 api호출
                     }
                    1->
                    {
                        //후기 많은 순 api호출
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        //api호출
        viewModel.getHospitalApiResponse()

        //리싸이클러뷰 초기화
        val recyclerView = binding.hospitalList
        viewModel.hospitalData.observe(this, {hospitals->
            hospitalAdapter.updateSearchList(hospitals)
        })
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastItem = layoutManager.findLastVisibleItemPosition()
                if(viewModel.hasNextPage == true && lastItem == totalItemCount-1)
                {
                    viewModel.getHospitalApiResponse()
                }
            }
        })



        viewModel.isresultOfSearch.observe(this,{
            istrue->
                //검색했음
                    viewModel.nameofHospitalData.observe(this,{
                            hospitals->
                        hospitalAdapter.updateSearchList(hospitals)
                        Log.e("eee","edkd")
                    })



        })
        hospitalAdapter = HospitalAdapter(emptyList())
        recyclerView.adapter = hospitalAdapter





    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}