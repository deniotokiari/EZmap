package by.deniotokiari.utils.android

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationLiveData(private val context: Context) : LiveData<Location>() {

    private var locationProvider: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null

    private fun createLocationProvider() {
        locationProvider = LocationServices.getFusedLocationProviderClient(context)
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { postValue(it) }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLastLocation() {
        locationProvider?.lastLocation?.addOnSuccessListener {
            postValue(it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        locationCallback?.let {
            locationProvider?.requestLocationUpdates(
                LocationRequest()
                    .setFastestInterval(DateUtils.SECOND_IN_MILLIS * 1)
                    .setInterval(DateUtils.SECOND_IN_MILLIS * 2)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
                it,
                null
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        createLocationProvider()
        requestLastLocation()
        createLocationCallback()
        requestLocationUpdates()
    }

    override fun onInactive() {
        locationCallback?.let { locationProvider?.removeLocationUpdates(it) }
        locationCallback = null
        locationProvider = null
    }
}