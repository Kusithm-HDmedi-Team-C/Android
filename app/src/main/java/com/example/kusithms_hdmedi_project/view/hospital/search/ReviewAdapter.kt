package com.example.kusithms_hdmedi_project.view.hospital.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.model.Review
import com.willy.ratingbar.ScaleRatingBar

class ReviewAdapter(private var reviews : List<Review>, private val listener: onReviewClickListener): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val createDate : TextView = itemView.findViewById(R.id.createDate)
        val rating : ScaleRatingBar = itemView.findViewById(R.id.ratingbar)
        val reviewContent : TextView = itemView.findViewById(R.id.reviewContent)
        val mainDoctor : TextView = itemView.findViewById(R.id.maindoctor)
        val reviewPrice : TextView = itemView.findViewById(R.id.reviewerPrice)
        //val examination : TextView = itemView.findViewById(R.id.examination)
        val exam1:TextView = itemView.findViewById(R.id.reviewerService1)
        val exam2:TextView = itemView.findViewById(R.id.reviewerService2)
        val exam3:TextView = itemView.findViewById(R.id.reviewerService3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = reviews[position]
        val subCreatedDate = review.createdAt.substringBefore("T")
        holder.createDate.text = subCreatedDate
        holder.rating.rating = review.rating.toFloat()
        holder.reviewContent.text = review.content
        holder.mainDoctor.text = review.doctor
        holder.reviewPrice.text = review.price.toString()

        val examinations = review.examinations.joinToString { ", " }
        val examList = examinations.split(", ")
        if(examList.size >0) holder.exam1.text = examList[0]
        if(examList.size >1) holder.exam2.text = examList[1]
        if(examList.size >2) holder.exam3.text = examList[2]

        val examTextList = listOf(holder.exam1,holder.exam2,holder.exam3)
        for((index, exam) in examList.withIndex())
        {
            if(index < examTextList.size)
            {
                val textView = examTextList[index]
                textView.text = exam

                if(exam == "CAT")
                {
                    textView.text = "CAT 검사"
                }
                if(exam == "FULL_BATTERY")
                {
                    textView.text = "풀배터리 검사"
                }
                if(exam == "EEG")
                {
                    textView.text = "정량 뇌파 검사"
                }
                if(exam == "COUNSEL")
                {
                    textView.setBackgroundResource(R.drawable.dental_type2)
                    val color = ContextCompat.getColor(holder.itemView.context, R.color.category_red_text)
                    textView.setTextColor(color)
                    textView.text = "상담"
                }
                if(exam == "MEDICINE")
                {
                    textView.setBackgroundResource(R.drawable.dental_type2)
                    val color = ContextCompat.getColor(holder.itemView.context, R.color.category_red_text)
                    textView.setTextColor(color)
                    textView.text = "약 처방"
                }
                if(exam == "ETC")
                {
                    textView.setBackgroundResource(R.drawable.dental_type3)
                    val color = ContextCompat.getColor(holder.itemView.context, R.color.category_blue_text)
                    textView.setTextColor(color)
                    textView.text = "기타"
                }
            }
        }


        holder.itemView.setOnClickListener{
            listener.onReviewClickListener()
        }
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}