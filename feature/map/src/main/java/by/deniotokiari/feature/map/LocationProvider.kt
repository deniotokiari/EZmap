package by.deniotokiari.feature.map

import android.location.Location
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider

class LocationProvider : IMyLocationProvider {

    private var locationConsumer: IMyLocationConsumer? = null
    private var lastLocation: Location? = null

    override fun startLocationProvider(myLocationConsumer: IMyLocationConsumer?): Boolean {
        locationConsumer = myLocationConsumer

        return true
    }

    override fun stopLocationProvider() {
        locationConsumer = null
    }

    override fun getLastKnownLocation(): Location = lastLocation ?: Location("")

    override fun destroy() {
        locationConsumer = null
    }

    fun onLocationChanged(location: Location) {
        lastLocation = location
        locationConsumer?.onLocationChanged(location, this)
    }
}