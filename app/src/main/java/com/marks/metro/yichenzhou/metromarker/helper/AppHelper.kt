package com.marks.metro.yichenzhou.metromarker.helper

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.marks.metro.yichenzhou.metromarker.model.MetroStation
import java.nio.charset.Charset

/**
 * Created by yichenzhou on 9/29/17.
 */

object AppHelper {
    val TAG = "AppHelper"
    // Extension for AssetManager
    fun AssetManager.fileAsString(fileName: String): String {
        return open(fileName).use {
            it.readBytes().toString(Charset.defaultCharset())
        }
    }

    fun loadStationsData(fileName: String, context: Context): MetroStation? {
        try {
            val csvStr = context.assets.fileAsString(fileName)
            val lines = csvStr.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (line in lines) {
                Log.d(TAG, line)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        return  null
    }

}