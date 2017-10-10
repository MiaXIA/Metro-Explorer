package com.marks.metro.yichenzhou.metromarker.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.marks.metro.yichenzhou.metromarker.R
import kotlinx.android.synthetic.main.landmark_detail.*

class LandMarkDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landmark_detail)

        add_favourite_button.setOnClickListener {
            //TODO
            //add the land mark to favourite list
        }

        share_link_button.setOnClickListener {
            //TODO
            //share the land mark via link
        }

        go_button.setOnClickListener {
            //TODO
            //get the direction via google map
        }
    }


}
