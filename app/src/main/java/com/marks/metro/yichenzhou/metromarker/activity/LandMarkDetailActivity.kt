package com.marks.metro.yichenzhou.metromarker.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.marks.metro.yichenzhou.metromarker.R
import kotlinx.android.synthetic.main.landmark_detail.*

class LandMarkDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landmark_detail)

        //setup toolbar
        setSupportActionBar(option_toolbar)

        go_button.setOnClickListener {
            //TODO
            //get the direction via google map
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //TODO
        val id = item?.itemId
        if(id == R.id.favorite_option) {
            //add to favorite land mark list
        } else if (id == R.id.share_option) {
            //share the link
        } else {
            //error
        }
        return super.onOptionsItemSelected(item)
    }
}
