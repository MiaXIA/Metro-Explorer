package com.marks.metro.yichenzhou.metromarker.activity

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.marks.metro.yichenzhou.metromarker.R
import com.marks.metro.yichenzhou.metromarker.helper.AppHelper
import com.marks.metro.yichenzhou.metromarker.model.MetroStation
import io.realm.Realm
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.main_menu.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync

class MenuActivity : AppCompatActivity(), OnMapReadyCallback {
    private val TAG = "MenuActivity"
    private var realm: Realm by Delegates.notNull()
    private var locationManager: LocationManager? = null
    private var mapFragment: SupportMapFragment? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        // Properties Initialization
        Realm.init(applicationContext)
        this.realm = Realm.getDefaultInstance()
        this.locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        this.mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?

        try {
            // We use location manager to fetch current location
            this.locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch (e: SecurityException) {
            Log.e(TAG, e.message)
        }

        //setup toolbar
        setSupportActionBar(station_filter_toolbar)


        favorite_button.setOnClickListener {
            //favorite button listener
            loadFavoriteData()
        }

        explore_button.setOnClickListener {
            //explore button listener
            exploreMetroStation()

        }
    }

    fun loadFavoriteData() {
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

    override fun onMapReady(p0: GoogleMap?) {
        val location = LatLng(this.latitude, this.longitude)
        if (p0 !is GoogleMap) {
            Log.e(TAG, "Invalid type for p0")
            return
        }
        p0.addMarker(MarkerOptions().position(location).title("My Location"))
        p0.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            latitude = location.latitude
            longitude = location.longitude
            if (mapFragment != null) {
                mapFragment!!.getMapAsync(this@MenuActivity)
            } else {
                Log.e(TAG, "Invalid type for mapFragment")
            }
            Log.d(TAG, "(Latitude, Longitude): " + location.latitude + "," + location.longitude)
//            val location = location.latitude.toString() + ", " + location.longitude.toString()
//            AppHelper.searchNearbyMerto(location, applicationContext)
        }


        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.stationfilter, menu)

        //TODO
        //MenuItem searchItem = menu.findItem(R.id.station_filter_search)
        //SearchView() searchView = (SearchView) MenuItemCompat.getActionView(searchItem)

        return super.onCreateOptionsMenu(menu)
    }
}

