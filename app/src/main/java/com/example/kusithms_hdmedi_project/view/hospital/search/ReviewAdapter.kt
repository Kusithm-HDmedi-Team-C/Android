package com.example.kusithms_hdmedi_project.view.hospital.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kusithms_hdmedi_project.R
import com.example.kusithms_hdmedi_project.model.Review
import com.example.kusithms_hdmedi_project.viewmodel.HospitalSearchViewModel
import com.willy.ratingbar.ScaleRatingBar

class ReviewAdapter(var reviews : List<Review>, private val viewModel:HospitalSearchViewModel): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    //private lateinit var viewModel:HospitalSearchViewModel
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val createDate : TextView = itemView.findViewById(R.id.createDate)
        val rating : ScaleRatingBar = itemView.findViewById(R.id.ratingbar)
        val reviewContent : TextView = itemView.findViewById(R.id.reviewContent)
        val mainDoctor : TextView = itemView.findViewById(R.id.maindoctor)
        val reviewPrice : TextView = itemView.findViewById(R.id.reviewerPrice)
        val exam1:TextView = itemView.findViewById(R.id.cat)
        val exam2:TextView = itemView.findViewById(R.id.fullbattery)
        val exam3:TextView = itemView.findViewById(R.id.eeg)
        val exam4:TextView = itemView.findViewById(R.id.counsel)
        val exam5:TextView = itemView.findViewById(R.id.medicine)
        val exam6:TextView = itemView.findViewById(R.id.other)
        val reviewInfo:ConstraintLayout = itemView.findViewById(R.id.reviewInfo)
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
        val won:String="원"
        var str = review.price.toString()
        holder.reviewPrice.text = str + "" + won

        holder.reviewInfo.visibility = if(review.isVisible) View.VISIBLE else View.GONE

        holder.reviewContent.setOnClickListener{
            viewModel.reviewVisibility(position)
            Log.e("fuck","${review.isVisible}")
        }
        for (exam in review.examinations) {
            when (exam) {
                "CAT" -> holder.exam1.visibility = View.VISIBLE
                "FULL_BATTERY" -> holder.exam2.visibility = View.VISIBLE
                "EEG" -> holder.exam3.visibility = View.VISIBLE
                "COUNSEL" -> holder.exam4.visibility = View.VISIBLE
                "MEDICINE" -> holder.exam5.visibility = View.VISIBLE
                "ETC" -> holder.exam6.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}