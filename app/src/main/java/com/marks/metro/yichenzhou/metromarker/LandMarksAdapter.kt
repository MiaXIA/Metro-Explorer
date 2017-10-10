package com.marks.metro.yichenzhou.metromarker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by mc.xia on 2017/10/9.
 */

class LandMarksAdapter() : RecyclerView.Adapter<LandMarksAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        //TO DO
    }

    override fun getItemCount(): Int {
        //TO DO
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return ViewHolder(layoutInflater.inflate(R.layout.row_landmark, parent, false))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //TO DO
        //private val landmarkImageView: ImageView
        //private val landmarkTextView: TextView

        //init {

        //}
    }
}