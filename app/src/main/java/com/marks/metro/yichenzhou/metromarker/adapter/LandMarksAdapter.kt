package com.marks.metro.yichenzhou.metromarker.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.marks.metro.yichenzhou.metromarker.R

/**
 * Created by mc.xia on 2017/10/9.
 */

class LandMarksAdapter() : RecyclerView.Adapter<LandMarksAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        //TODO
    }

    override fun getItemCount(): Int {
        //TODO
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return ViewHolder(layoutInflater.inflate(R.layout.row_landmark, parent, false))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //TODO
        //private val landmarkImageView: ImageView
        //private val landmarkTextView: TextView

        //init {

        //}
    }
}