package com.marks.metro.yichenzhou.metromarker.activity

import android.content.Context
import android.content.pm.PackageManager
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.Menu
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.marks.metro.yichenzhou.metromarker.R
import com.marks.metro.yichenzhou.metromarker.helper.AppHelper
import com.marks.metro.yichenzhou.metromarker.helper.LocationDetector
import io.realm.Realm
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.main_menu.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync

class MenuActivity : AppCompatActivity(), LocationDetector.LocationListener, OnMapReadyCallback {
    private val TAG = "MenuActivity"
    private var realm: Realm by Delegates.notNull()
    private var locationManager: LocationManager? = null
    private var mapFragment: SupportMapFragment? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var locationDetector: LocationDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        this.requestPermission()

        // Properties Initialization
        Realm.init(applicationContext)
        this.realm = Realm.getDefaultInstance()
        this.locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        this.mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        this.locationDetector = LocationDetector(this)
        this.locationDetector.locationListener = this

        // Set default location on the MAP


        // Fetch current location and show on the mapView
        if (AppHelper.checkPermissionStatus(AppHelper.LOCATION_DEFAULT_CODE, this)) {
            this.locationDetector.detectLocation()
        }

        //setup toolbar
        setSupportActionBar(station_filter_toolbar)

        //TODO
        //favorite_button.setOnClickListener {
            //favorite button listener
            //loadFavoriteData()
        //}

        explore_button.setOnClickListener {
            //explore button listener
            exploreMetroStation()

        }
    }

    private fun loadFavoriteData() {
        doAsync {
            activityUiThread {
                //TODO
                //load the favorite list data and jump to the List UI
                val intent = Intent(this@MenuActivity, LandMarksActivity::class.java)

                startActivity(intent)
            }
        }
    }

    fun exploreMetroStation() {
        //TODO
        //searchview text filled detect
    }

    override fun onMapReady(map: GoogleMap?) {
        val location = LatLng(this.latitude, this.longitude)
        if (map !is GoogleMap) {
            Log.e(TAG, "Invalid type for map")
            return
        }
        map.addMarker(MarkerOptions().position(location).title("My Location"))
        map.setMaxZoomPreference(15.0f)
        map.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppHelper.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.first() != PackageManager.PERMISSION_GRANTED) {
                this.requestPermission()
            }
        }
        // Where to fetch location again after location permission granted
        this.locationDetector.detectLocation()
    }

    private fun requestPermission() {
        if (!AppHelper.checkPermissionStatus(AppHelper.LOCATION_DEFAULT_CODE, this)) {
            ActivityCompat.requestPermissions(this, arrayOf(AppHelper.LOCATION_DEFAULT_CODE), AppHelper.LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun locationNotFound(reason: LocationDetector.FailureReason) {
        when(reason) {
            LocationDetector.FailureReason.TIMEOUT -> Log.e(TAG, "Location Detection Time Out")
            LocationDetector.FailureReason.NO_PERMISSION -> Log.e(TAG, "No Permission to detect location")
        }
    }

    override fun locationFound(location: Location) {
        this.latitude = location.latitude
        this.longitude = location.longitude
        mapFragment!!.getMapAsync(this@MenuActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.stationfilter, menu)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        //TODO
        //use the text to filter station
        return super.onPrepareOptionsMenu(menu)
    }
}

