package com.marks.metro.yichenzhou.metromarker.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.marks.metro.yichenzhou.metromarker.R
import com.marks.metro.yichenzhou.metromarker.helper.AppHelper
import com.marks.metro.yichenzhou.metromarker.model.Landmark
import kotlinx.android.synthetic.main.metro_detail.*


class MetroDetailActivity : AppCompatActivity(), OnMapReadyCallback, AppHelper.YelpAPICompletionListener {
    private val TAG = "MetroDetailActivity"
    private lateinit var metroName: String
    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var mapFragment: SupportMapFragment
    private var landmarkList = ArrayList<Landmark>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.metro_detail)

        // Fetch data from intent extras
        this.metroName = intent.getStringExtra("name")
        this.latitude = intent.getDoubleExtra("lang", 0.0)
        this.longitude = intent.getDoubleExtra("long", 0.0)
        // Set App bar title
        this.station_filter_toolbar.title = this.metroName

        // Properties Initialization
        this.mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        // Fetch current location and show on the mapView
        this.mapFragment.getMapAsync(this@MetroDetailActivity)
        // Fetch data from Yelp
        AppHelper.yelpAPIListener = this
        if (this.latitude != null && this.longitude != null) {
            AppHelper.yelpLandmarkFetcher(this.latitude!!, this.longitude!!, this)
        }
        //TODO
        //set listview adapter and clicklistener
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map == null) {
            Log.e(TAG, "map is null")
            return
        }
        if (this.latitude == null || this.longitude == null) {
            Log.e(TAG, "Lantitude or longitude is null")
            return
        }
        val builder = LatLngBounds.Builder()
        val location = LatLng(this.latitude!!, this.longitude!!)
        builder.include(location)
        map.clear()
        map.addMarker(MarkerOptions().position(location).title(this.metroName).icon(this.markerIcon(BitmapDescriptorFactory.HUE_AZURE)))
        map.setMaxZoomPreference(17.0f)
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0))
    }

    private fun markerIcon(color: Float): BitmapDescriptor {
        return BitmapDescriptorFactory.defaultMarker(color)
    }

    override fun yelpDataFetched() {
        this.landmarkList.removeAll { true }
        this.landmarkList = AppHelper.landmarkList
        for (landmark in this.landmarkList) {
            Log.d(TAG, landmark.toString())
        }
    }

    override fun yelpDataNotFetched() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
