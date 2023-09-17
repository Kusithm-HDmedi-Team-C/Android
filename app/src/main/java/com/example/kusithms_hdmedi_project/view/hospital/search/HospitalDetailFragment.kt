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

class HospitalDetailFragment : Fragment(){
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
        val recyclerView = binding.reviewRecycler

        val adapter = ReviewAdapter(emptyList(),viewModel)
        recyclerView.adapter = adapter

        viewModel.reviewData.observe(viewLifecycleOwner)
        {
                reviews->
            adapter.reviews = reviews
            adapter.notifyDataSetChanged()
        }
        viewModel.itemChangedIndex.observe(viewLifecycleOwner) { index ->
            adapter.notifyItemChanged(index)
        }




        viewModel.detailHospital.observe(viewLifecycleOwner){
            detailList->
            val name = detailList[0].name
            val rating = detailList[0].averageRating
            //잘못됨
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
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(urlAddress)
                startActivity(intent)
            }

        }







    }

    override fun onResume() {
        super.onResume()
    }



}