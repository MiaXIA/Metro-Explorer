package com.marks.metro.yichenzhou.metromarker.helper

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.koushikdutta.ion.Ion
import com.marks.metro.yichenzhou.metromarker.model.MetroStation
import io.realm.Realm
import java.nio.charset.Charset

/**
 * Created by yichenzhou on 9/29/17.
 */

object AppHelper {
    val TAG = "AppHelper"
    val GOOGLE_PLACE_KEY = "AIzaSyAGQWfAqWM8pzYtjbHIN_hhNhcE4BzS2UU"
    val GOOGLE_PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"

    // Extension for AssetManager
    private fun AssetManager.fileAsString(fileName: String): String {
        return open(fileName).use {
            it.readBytes().toString(Charset.defaultCharset())
        }
    }

    // Load local csv file into Metro Station Realm Database
    fun loadStationsData(fileName: String, context: Context): MetroStation? {
        try {
            val realm = Realm.getDefaultInstance()
            val csvStr = context.assets.fileAsString(fileName)
            val lines = csvStr.split(";\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (line in lines) {
                val units = line.split(",")
                //Make sure data format is correct
                if (units.count() != 7) {
                    throw  Exception("Invalid data format")
                }
                realm.executeTransaction {
                    val station = realm.createObject(MetroStation::class.java)
                    station.setValues(units)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        return  null
    }

    // Search nearby metro stations based on device's last known location
    fun searchNearbyMerto(location: String, context: Context) {
        Ion.with(context).load(GOOGLE_PLACES_SEARCH_URL)
                .addQuery("location", location)
                .addQuery("radius", "1000")
                .addQuery("types", "subway_station")
                .addQuery("key", GOOGLE_PLACE_KEY)
                .asJsonObject()
                .setCallback { e, result ->
                    if (e != null) {
                        Log.e(TAG, "Google Place Request Error: ${e.toString()}")
                    }

                    result?.let {
                        if (result["status"].asString != "OK") {
                            Log.d(TAG, "Failed to fetch data: ${result["status"].asString}")
                        }

                        val rootDataArr = result["results"].asJsonArray
                        for (data in rootDataArr) {
                            val indexDataArr = data.asJsonObject
                            val name = indexDataArr["name"]
                            val placeID = indexDataArr["place_id"]
                            Log.d(TAG, "Location Detail, Name: ${name}, ID: ${placeID}")
                        }
                    }
                }
    }
}