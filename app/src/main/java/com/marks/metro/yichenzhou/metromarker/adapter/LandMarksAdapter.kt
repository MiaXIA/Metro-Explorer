package com.marks.metro.yichenzhou.metromarker.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.marks.metro.yichenzhou.metromarker.R
import com.marks.metro.yichenzhou.metromarker.model.Landmark

/**
 * Created by mc.xia on 2017/10/9.
 */

class LandMarksAdapter(landmarks: ArrayList<Landmark>) : RecyclerView.Adapter<LandMarksAdapter.ViewHolder>() {
    val landmarkList = landmarks
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val landmark = this.landmarkList?.get(position)
        landmark?.let { (holder as ViewHolder).bind(landmark, position) }
    }

    override fun getItemCount(): Int {
        return this.landmarkList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return ViewHolder(layoutInflater.inflate(R.layout.row_landmark, parent, false))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.nameText)
        private val ratingTextView: TextView = view.findViewById(R.id.ratingText)
        private val infoTextView: TextView = view.findViewById(R.id.infoText)

        fun bind(landmark: Landmark, position: Int) {
             nameTextView.text = (position + 1).toString() + ". " + landmark.name
             ratingTextView.text = landmark.rating.toString() + "/5.0"
             infoTextView.text = landmark.category
         }
    }
}