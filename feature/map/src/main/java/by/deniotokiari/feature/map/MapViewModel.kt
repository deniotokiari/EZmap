package by.deniotokiari.feature.map

import android.content.SharedPreferences
import android.content.res.Resources
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import by.deniotokiari.utils.android.get
import by.deniotokiari.utils.android.getDouble
import by.deniotokiari.utils.android.set

private const val KEY_ZOOM_LEVEL = "KEY_ZOOM_LEVEL"
private const val KEY_MAP_LOCATION = "KEY_MAP_LOCATION"

class MapViewModel(
    private val resources: Resources,
    private val preferences: SharedPreferences,
    val locationLiveData: LiveData<Location>
) : ViewModel() {

    private val maxZoomLevel: Double by lazy { resources.getDouble(R.string.map_max_zoom_level) }
    private val minZoomLevel: Double by lazy { resources.getDouble(R.string.map_min_zoom_level) }

    private val _zoomLevel = MediatorLiveData<Double>().apply {
        val defaultZoomLevel = resources.getDouble(R.string.map_default_zoom_level)
        val focusedZoomLevel = resources.getDouble(R.string.map_focused_zoom_level)

        value = preferences[KEY_ZOOM_LEVEL, defaultZoomLevel]

        addSource(locationLiveData) {
            if (value == defaultZoomLevel) {
                value = focusedZoomLevel
            }

            removeSource(locationLiveData)
        }
    }
    val zoomLevel: LiveData<Double> by this::_zoomLevel


    private val _mapLocation = MediatorLiveData<Location>().apply {
        preferences.location()?.let { value = it }

        addSource(locationLiveData) {
            if (value == null) {
                value = it
            }

            removeSource(locationLiveData)
        }
    }
    val mapLocation: LiveData<Location> by this::_mapLocation

    fun saveZoomLevel(zoomLevel: Double) {
        preferences[KEY_ZOOM_LEVEL] = zoomLevel
    }

    fun saveMapLocation(longitude: Double, latitude: Double) {
        preferences[KEY_MAP_LOCATION] = "$longitude $latitude"
    }

    fun moveToCurrentLocation() {
        _mapLocation.value = locationLiveData.value
    }

    fun zoomIn() {
        _zoomLevel.value
            ?.takeIf { it + 1 < maxZoomLevel }
            ?.let { _zoomLevel.value = it + 1 }
    }

    fun zoomOut() {
        _zoomLevel.value
            ?.takeIf { it - 1 > minZoomLevel }
            ?.let { _zoomLevel.value = it - 1 }
    }
}

private fun SharedPreferences.location(): Location? {
    return this[KEY_MAP_LOCATION, ""]
        .takeIf { it.isNotEmpty() }
        ?.split(" ")
        ?.let {
            Location("").apply {
                longitude = it[0].toDouble()
                latitude = it[1].toDouble()
            }
        }
}