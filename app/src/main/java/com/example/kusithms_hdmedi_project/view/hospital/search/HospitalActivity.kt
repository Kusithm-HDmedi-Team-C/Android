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
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.ActivityHospitalBinding
import com.example.kusithms_hdmedi_project.model.Hospital
import com.example.kusithms_hdmedi_project.model.Hospitals
import com.example.kusithms_hdmedi_project.view.MainActivity
import com.example.kusithms_hdmedi_project.view.diagnosis.DiagnosisPrevActivity
import com.example.kusithms_hdmedi_project.view.hospital.review.WriteReviewActivity
import com.example.kusithms_hdmedi_project.viewmodel.HospitalSearchViewModel

class HospitalActivity : AppCompatActivity(),onDetailClickListener {
    private var _binding : ActivityHospitalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HospitalSearchViewModel by viewModels()
    private lateinit var hospitalAdapter: HospitalAdapter

    override fun onDetailItemClicked(hospitalId:Int)
    {
        viewModel.getHospitalDetail(hospitalId)
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentDetail.id,HospitalDetailFragment())
            .addToBackStack(null)
            .commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHospitalBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)
        val recyclerView = binding.hospitalList

        val backbtn = binding.backbtn
        val searchbox = binding.searchbox
        //var current = 0
        val spinner = binding.menu


        backbtn.setOnClickListener{
            onBackPressed()
        }
        binding.reviewWritebtn.setOnClickListener{
            val intent = Intent(this@HospitalActivity, WriteReviewActivity::class.java)
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
            viewModel.changefocus()
            viewModel.changeSearchState()
            searchbox.setText("")
            searchbox.clearFocus()
        }
        if(viewModel.isFocus.value == true)
        {
            //edittext 위치 동적 변경
            val params = searchbox.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = 19
            searchbox.layoutParams = params
        }

        //focus시 fragment로 이동
        searchbox.setOnFocusChangeListener(object:OnFocusChangeListener{
            override fun onFocusChange(view: View, hasFocus:Boolean)
            {
                if(hasFocus)
                {
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

        val scrollListener = object :RecyclerView.OnScrollListener()
        {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if(totalItemCount <= lastVisibleItem+2)
                {
                    when(spinner.selectedItemPosition){
                        0 -> viewModel.getHospitalApiResponse("averageRating", viewModel.currentPage)
                        1 -> viewModel.getHospitalApiResponse("numberOfReviews", viewModel.currentPage)
                    }
                }
            }
        }



        val arrayList=ArrayList<String>()
        arrayList.add("만족도 순")
        arrayList.add("후기 많은 순")
        spinner.adapter = SpinnerAdapter(this, R.layout.item_spinner_option,arrayList)
//        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown,arrayList)
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
//        spinner.adapter = adapter

        spinner.post{
            spinner.dropDownVerticalOffset = spinner.height
        }
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //val selectedItem = p0?.getItemAtPosition(p2).toString()

                when(position){
                     0 -> {
                         //만족도 순 정렬 api호출
                         viewModel.setnulldata()
                         viewModel.currentPage = 0
                         viewModel.getHospitalApiResponse("averageRating",viewModel.currentPage)
                         //hospitalAdapter.updateSearchList(viewModel.hospitalData.value!!)
                         Log.e("91","${viewModel.hospitalData.value}")}

                    1->
                    {//후기 많은 순 api호출
                        viewModel.setnulldata()
                        viewModel.currentPage = 0
                        viewModel.getHospitalApiResponse("numberOfReviews",viewModel.currentPage)
                        //hospitalAdapter.updateSearchList(viewModel.hospitalData.value!!)
                        Log.e("91","${viewModel.hospitalData.value}")
                    }
                }
                (spinner.adapter as SpinnerAdapter).selectedPosition = position
                (spinner.adapter as SpinnerAdapter).notifyDataSetChanged()
                recyclerView.addOnScrollListener(scrollListener)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.getHospitalApiResponse("averageRating",0)
            }
        }

        //api호출
        //viewModel.getHospitalApiResponse("averageRating",0)

        var hospitalDataObserver: Observer<List<Hospitals>>?= null
        var nameofHospitalDataObserver: Observer<List<Hospitals>>?= null

        viewModel.isresultOfSearch.observe(this, { isTrue ->
            // 이전에 Observe했던 Observer를 제거
            nameofHospitalDataObserver?.let { viewModel.nameofHospitalData.removeObserver(it) }
            hospitalDataObserver?.let { viewModel.hospitalData.removeObserver(it) }

            if (isTrue) {
                nameofHospitalDataObserver = Observer { hospitals ->
                    hospitalAdapter.updateSearchList(hospitals)
                }
                viewModel.nameofHospitalData.observe(this, nameofHospitalDataObserver!!)
            } else {
                hospitalDataObserver = Observer { hospitals ->
                    hospitalAdapter.updateSearchList(hospitals)
                }
                viewModel.hospitalData.observe(this, hospitalDataObserver!!)
            }
        })


    hospitalAdapter = HospitalAdapter(emptyList(),this)
        recyclerView.adapter = hospitalAdapter

    }






    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}