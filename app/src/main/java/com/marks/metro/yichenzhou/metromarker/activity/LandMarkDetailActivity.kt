package com.marks.metro.yichenzhou.metromarker.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.marks.metro.yichenzhou.metromarker.R
import com.marks.metro.yichenzhou.metromarker.adapter.LandMarksAdapter
import com.marks.metro.yichenzhou.metromarker.adapter.LandmarkDetailAdapter
import com.marks.metro.yichenzhou.metromarker.model.Landmark
import kotlinx.android.synthetic.main.landmark_detail.*
import kotlinx.android.synthetic.main.main_menu.*

class LandMarkDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private val TAG = "LandmarkDetailActivity"
    private var landmark = Landmark()

    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landmark_detail)

        this.landmark.parseIntentData(intent)
        this.mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        //setup toolbar title
        this.landmark_filter_toolbar.title = this.landmark.name
        
        if (this.landmark.latitude != 0.0 && this.landmark.longitude != 0.0) {
            this.mapFragment.getMapAsync(this)
        }
        this.nameTextView.text = this.landmark.name
        this.ratingAndReviewTextView.text = "Rating ${landmark.rating}/5.0 || ${landmark.reviewCount} Reviews"
        this.categoryTextView.text = "${landmark.category} || price: ${landmark.price}"
        this.addressTextView.text = "${landmark.address}"
    }
    
    override fun onMapReady(map: GoogleMap?) {
        if (map == null) {
            Log.e(TAG, "map is null")
            return
        }

        val builder = LatLngBounds.Builder()
        val location = LatLng(this.landmark.latitude, this.landmark.longitude)
        builder.include(location)
        map.addMarker(MarkerOptions().position(location).title(this.landmark.name).icon(this.markerIcon(com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE)))
        map.setMaxZoomPreference(17.0f)
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.12).toInt()
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), width, height, padding))
    }

    private fun markerIcon(color: Float): BitmapDescriptor {
        return BitmapDescriptorFactory.defaultMarker(color)
    }

    private fun addOrRemovefavoriate() {

    }
}
