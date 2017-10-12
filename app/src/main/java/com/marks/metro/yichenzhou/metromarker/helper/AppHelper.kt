package com.marks.metro.yichenzhou.metromarker.helper

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.support.v4.content.ContextCompat
import android.util.Log
import com.koushikdutta.ion.Ion
import com.marks.metro.yichenzhou.metromarker.model.MetroStation
import io.realm.Case
import io.realm.Realm
import java.nio.charset.Charset

/**
 * Created by yichenzhou on 9/29/17.
 */

object AppHelper {
    val TAG = "AppHelper"
    val GOOGLE_PLACES_KEY = "AIzaSyAGQWfAqWM8pzYtjbHIN_hhNhcE4BzS2UU"
    val GOOGLE_PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"

    val LOCATION_PERMISSION_REQUEST_CODE: Int = 777
    val NETWORK_PERMISSION_REQUEST_CODE: Int = 778

    val LOCATION_DEFAULT_CODE: String = android.Manifest.permission.ACCESS_FINE_LOCATION
    val NETWORK_DEFAULT_CODE: String = android.Manifest.permission.ACCESS_NETWORK_STATE

    var placeNameList = ArrayList<String>()
    lateinit var placeAPIListener: GooglePlacesAPICompletionListener
    interface GooglePlacesAPICompletionListener {
        fun dataFetched()
        fun dataNotFetched()
    }

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

    // Search typed metro from TextField
    // Return searched metro
    fun searchTextMetro(content: String): List<MetroStation> {
        val realm = Realm.getDefaultInstance()
        return realm.where(MetroStation::class.java).contains("name", content, Case.INSENSITIVE).findAll()
    }


    // Search nearby metro stations based on device's last known location
    // Return or callback will give nearby metro stations
    fun searchNearbyMerto(location: String, context: Context) {
        Ion.with(context).load(GOOGLE_PLACES_SEARCH_URL)
                .addQuery("location", location)
                .addQuery("radius", "1000")
                .addQuery("types", "subway_station")
                .addQuery("key", GOOGLE_PLACES_KEY)
                .asJsonObject()
                .setCallback { e, result ->
                    if (e != null) {
                        this.placeAPIListener.dataNotFetched()
                        Log.e(TAG, "Google Place Request Error: ${e.message}")
                    }

                    result?.let {
                        if (result["status"].asString != "OK") {
                            this.placeAPIListener.dataNotFetched()
                            Log.d(TAG, "Failed to fetch data: ${result["status"].asString}")
                        }
                        this.placeNameList.removeAll { true }
                        val rootDataArr = result["results"].asJsonArray
                        for (data in rootDataArr) {
                            val indexDataArr = data.asJsonObject
                            var name = indexDataArr["name"].toString()
                            name = strTrim(name)
                            val placeID = indexDataArr["place_id"].toString()
                            this.placeNameList.add(name)
                            Log.d(TAG, "Location Detail, Name: $name, ID: $placeID")
                        }
                        this.placeAPIListener.dataFetched()
                    }
                }
    }

    // Ask Package Manager is the permission granted
    fun checkPermissionStatus(serviceDefaultCode: String, context: Context): Boolean {
        val permissionStatus = ContextCompat.checkSelfPermission(context, serviceDefaultCode)
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            return true
        }

        return false
    }

    private fun strTrim(content: String): String {
        var copy = content.removeSurrounding("\"")
        val dataArr = copy.split(" ")
        copy = ""
        dataArr
                .filter { it != "Station" }
                .forEach{ copy += (it + " ") }
        return copy.trim()
    }
}