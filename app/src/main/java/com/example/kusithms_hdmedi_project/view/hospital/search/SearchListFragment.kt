package com.example.kusithms_hdmedi_project.view.hospital.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.FragmentSearchListBinding
import com.example.kusithms_hdmedi_project.viewmodel.HospitalSearchViewModel
import com.example.kusithms_hdmedi_project.view.hospital.search.onHospitalItemClickListener

class SearchListFragment : Fragment(),onHospitalItemClickListener {
    private var _binding : FragmentSearchListBinding? = null
    private val binding get() = _binding!!
    val viewModel by activityViewModels<HospitalSearchViewModel>()
    private lateinit var searchRecyclerAdapter: searchRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchListBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.searchResult



        //목록에 나타내기위한
        viewModel.nameofHospitalData.observe(viewLifecycleOwner){
            hospitals->
            searchRecyclerAdapter = searchRecyclerAdapter(hospitals,this)
            recyclerView.adapter = searchRecyclerAdapter
        }
        val adapter = searchRecyclerAdapter(emptyList(), this)
        recyclerView.adapter = adapter

    }
    override fun onHospitalItemClicked(hospitalName:String)
    {
        //api호출로 데이터 넣어줌
        viewModel.getHospitalFromName(hospitalName)
        viewModel.changeSearchState()
        //뒤로가기
        parentFragmentManager.popBackStack()
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}