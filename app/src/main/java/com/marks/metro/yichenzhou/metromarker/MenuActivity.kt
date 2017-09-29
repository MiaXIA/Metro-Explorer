package com.marks.metro.yichenzhou.metromarker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.marks.metro.yichenzhou.metromarker.helper.AppHelper

class MenuActivity : AppCompatActivity() {

    val TAG = "MenuActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        AppHelper.loadStationsData("station.csv", applicationContext)
    }
}
