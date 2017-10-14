package com.marks.metro.yichenzhou.metromarker.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.marks.metro.yichenzhou.metromarker.R
import kotlinx.android.synthetic.main.landmark_list.*

class FavoriateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landmark_list)

        //setup toolbar
        setSupportActionBar(landmark_filter_toolbar)

        //TODO
        //setup recyclerView adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.landmarkfilter, menu)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        //TODO
        //use the text to filter the land mark
        super.onPrepareOptionsMenu(menu)

        return true
    }
}
