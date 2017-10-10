package com.marks.metro.yichenzhou.metromarker.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.marks.metro.yichenzhou.metromarker.R
import kotlinx.android.synthetic.main.metrostation_menu.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync

class MetroStationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.metrostation_menu)

        detail_button.setOnClickListener() {
            //detail button listener
            loadDetailData()
        }
    }

    fun loadDetailData() {
        doAsync {
            activityUiThread {
                //TODO
                //load the selected landmark detail data

                val intent = Intent(this@MetroStationActivity, LandMarkDetailActivity::class.java)

                startActivity(intent)
            }
        }
    }
}
