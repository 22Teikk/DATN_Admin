package com.teikk.datn_admin.view.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseActivity
import com.teikk.datn_admin.data.model.Order
import com.teikk.datn_admin.data.model.UserProfile
import com.teikk.datn_admin.data.service.ServiceLocation
import com.teikk.datn_admin.databinding.ActivityMapsBinding
import com.teikk.datn_admin.utils.ServiceManager
import com.teikk.datn_admin.utils.drawBitmapIntoVector
import com.teikk.datn_admin.utils.getAddressByLocation
import com.teikk.datn_admin.utils.roundBitmap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback, SensorEventListener {

    private val viewModel by viewModels<MapViewModel>()
    private lateinit var mapGG: GoogleMap
    private lateinit var sensorManager: SensorManager
    private var myMarker: Marker? = null

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }
    private val bitmap: Bitmap by lazy {
        Bitmap.createBitmap(
            350,
            350,
            Bitmap.Config.ARGB_8888
        )
    }

    private val myVectorLocation: Drawable by lazy {
        ContextCompat.getDrawable(this, R.drawable.icon_marker)!!
    }
    private val vectorDrawable: Drawable by lazy {
        ContextCompat.getDrawable(this, R.drawable.icon_radar)!!
    }

    private lateinit var userProfile: UserProfile
    private lateinit var order: Order


    override fun getLayoutResId(): Int {
        return R.layout.activity_maps
    }

    override fun init() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        userProfile = intent.getSerializableExtra("user") as UserProfile
        order = intent.getSerializableExtra("order") as Order
        Log.d("ajksdhfkajsdfhasd", order.toString())
        ServiceManager.startOrRestartService(this, ServiceLocation::class.java)
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        ServiceManager.stopService(this, ServiceLocation::class.java)
        super.onDestroy()
    }

    private fun getMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    mapGG.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                latitude,
                                longitude
                            ), 18f
                        )
                    )

                }
            }
            .addOnFailureListener { e ->

            }
    }

    private fun viewMap() {
        mapGG.uiSettings.isCompassEnabled = false
        mapGG.uiSettings.isMyLocationButtonEnabled = true
        mapGG.uiSettings.isMapToolbarEnabled = false
        mapGG.uiSettings.isZoomControlsEnabled = false
        mapGG.apply {
            val canvas = Canvas(bitmap)
            vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
            vectorDrawable.draw(canvas)
            myMarker = this.addMarker(
                MarkerOptions().position(
                    LatLng(0.0, 0.0)
                )
                    .zIndex(0f)
            )!!.apply {
                alpha = 0f
                setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
                isFlat = true
                setAnchor(0.5f, 0.5f)
            }
        }
    }

    private fun observerMap() = viewModel.mapType.observe(this) {
        mapGG.mapType = it
    }

    private fun eventMap() {
        with(binding) {
            btnBack.setOnClickListener {
                finish()
            }
            imgMapType.setOnClickListener {
                if (viewModel.mapType.value == 4) {
                    viewModel.changeTypeMap(1)
                } else {
                    viewModel.changeTypeMap(viewModel.mapType.value!! + 1)
                }
            }
            imgMyLocation.setOnClickListener {
                getMyLocation()
            }
            imgCustomerLocation.setOnClickListener {
                mapGG.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(userProfile.lat, userProfile.long), 18f
                    )
                )
            }
            imgCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL).apply {
                    data = Uri.parse("tel:${userProfile.phone}")
                }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MapsActivity, "No dialer app available", Toast.LENGTH_SHORT).show()
                }
            }
            imgDone.setOnClickListener {
                viewModel.updateOrder(order.copy(status = "Delivered")) {
                    finish()
                }
            }
        }
    }

    private fun getCustomerLocation() {
        mapGG.addMarker(
            MarkerOptions()
                .position(LatLng(userProfile.lat, userProfile.long))
                .title("Customer Location")
                .zIndex(2f)
        )
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mapGG = googleMap
        val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL)

        viewMap()
        getCustomerLocation()
        getMyLocation()
        observerMap()
        eventMap()
        ServiceLocation.locationLiveData.observe(this) { location ->
            createMyMarker(location.latitude, location.longitude)
            viewModel.sendLocationToCustomer(userProfile.id, order.id, location.latitude, location.longitude)
        }
    }

    private fun createMyMarker(lat: Double, long: Double) {
        mapGG.apply {
            Glide.with(this@MapsActivity)
                .asBitmap()
                .load(R.drawable.app_icon)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(80, 80)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(
                            drawBitmapIntoVector(
                                roundBitmap(resource, 80, 80),
                                myVectorLocation
                            )
                        )
                        myMarker?.apply {
                            position = LatLng(lat, long)
                            zIndex = 2f
                            alpha = 1f
                        }!!.apply {
                            setIcon(bitmapDescriptor)
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            val rotationMatrix = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            val orientationAngles = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)
            val azimuthInRadians = orientationAngles[0]
            val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).toFloat()
            updateMarkerRotation(azimuthInDegrees)
        }
    }

    private fun updateMarkerRotation(azimuthInDegrees: Float) {
        myMarker?.rotation = azimuthInDegrees
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}