package com.marks.metro.yichenzhou.metromarker.model

/**
 * Created by mc.xia on 2017/9/20.
 */

data class LandMark (val name:String, val longtitude: String, val latitude: String, val address: String, var distance: Int)
//少一个picture，查一下图片的存储格式再加