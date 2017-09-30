package com.marks.metro.yichenzhou.metromarker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.marks.metro.yichenzhou.metromarker.helper.AppHelper
import com.marks.metro.yichenzhou.metromarker.model.MetroStation
import io.realm.Realm
import kotlin.properties.Delegates

class MenuActivity : AppCompatActivity() {
    val TAG = "MenuActivity"
    var realm: Realm by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        Realm.init(applicationContext)
        this.realm = Realm.getDefaultInstance()

        val stationCount = this.realm.where(MetroStation::class.java).findAll().count()
        if (stationCount== 0) {
            Log.d(TAG, "No metro station data, working on it now")
            AppHelper.loadStationsData("Stations.csv", applicationContext)
            Log.d(TAG, "Done")
        } else {
            Log.d(TAG, "Metro station data exists, count: ${stationCount.toString()}")
        }
    }
}
