package com.marks.metro.yichenzhou.metromarker.model

/**
 * Created by mc.xia on 2017/9/20.
 */

data class MetroStation (val name:String, val longtitude: String, val latitude: String, val address: String, var color: Boolean)
//按我的理解color是为了显示当前选取的是不是这个station，所以只要用Boolean应该就可以，也可以改成String保存颜色
//test branch