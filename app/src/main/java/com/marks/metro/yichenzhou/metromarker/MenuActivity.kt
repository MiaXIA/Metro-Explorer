package com.marks.metro.yichenzhou.metromarker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        this.requestMetroList()
    }

    private fun requestMetroList() {
        FuelManager.instance.basePath = "https://api.wmata.com/Rail.svc/json/jStations"
        FuelManager.instance.baseHeaders = mapOf("api_key" to "6f41a1603c704b57ba2e1e5741f79450")
        Fuel.get("").response {
            request, response, result ->
            val (data, error) = result
            if (error != null) {
                println(error.toString())
                return@response
            }
            println(data)
        }
    }

}
