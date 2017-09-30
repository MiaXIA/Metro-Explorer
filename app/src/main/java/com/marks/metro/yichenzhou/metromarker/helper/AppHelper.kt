package com.marks.metro.yichenzhou.metromarker.helper

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.marks.metro.yichenzhou.metromarker.model.MetroStation
import io.realm.Realm
import java.nio.charset.Charset

/**
 * Created by yichenzhou on 9/29/17.
 */

object AppHelper {
    val TAG = "AppHelper"
    // Extension for AssetManager
    private fun AssetManager.fileAsString(fileName: String): String {
        return open(fileName).use {
            it.readBytes().toString(Charset.defaultCharset())
        }
    }

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
}