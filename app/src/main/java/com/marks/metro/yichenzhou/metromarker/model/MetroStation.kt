package com.marks.metro.yichenzhou.metromarker.model

import io.realm.RealmObject

/**
 * Created by yichenzhou on 9/20/17.
 */

open class MetroStation: RealmObject() {
    open var name: String = ""
    open var long: String = ""
    open var lang: String = ""
    open var address: String = ""

    open var street: String = ""
    open var city: String = ""
    open var state: String = ""
    open var zip: String = ""
}