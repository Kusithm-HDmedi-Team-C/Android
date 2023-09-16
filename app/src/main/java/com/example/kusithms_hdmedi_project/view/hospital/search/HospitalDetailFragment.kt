package com.example.kusithms_hdmedi_project.view.hospital.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.databinding.FragmentHospitalDetailBinding
import com.example.kusithms_hdmedi_project.viewmodel.HospitalSearchViewModel

class HospitalDetailFragment : Fragment(),onReviewClickListener {
    private var _binding:FragmentHospitalDetailBinding? = null
    private val binding get() = _binding!!
    val viewModel by activityViewModels<HospitalSearchViewModel>()
    private lateinit var ReviewAdapter : ReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_hospital_detail, container, false)
        _binding = FragmentHospitalDetailBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.detailHospital.observe(viewLifecycleOwner){
            detailList->
            val name = detailList[0].name
            val rating = detailList[0].averageRating
            val reviewNum = detailList[0].count
            val detailAddress = detailList[0].address
            val phnum = detailList[0].telephone
            val urlAddress = detailList[0].mapUrl
            binding.hospitalTitle.text = name
            binding.rating.text = rating.toString()
            binding.reviewNum.text = reviewNum.toString()
            binding.detailAddress.text = detailAddress
            binding.detailPhnum.text = phnum

            binding.gotoMap.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${urlAddress}"))
                startActivity(intent)
            }

        }
//
         val recyclerView = binding.reviewRecycler
//         val adapter = ReviewAdapter(emptyList(),this)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = adapter

        viewModel.reviewData.observe(viewLifecycleOwner)
        {
            reviews->
            ReviewAdapter = ReviewAdapter(reviews,this)
            recyclerView.adapter = ReviewAdapter
            Log.e("err", "${viewModel.reviewData.value}")
        }




    }

    override fun onReviewClickListener() {
        viewModel.updateReviewClick()
    }


}