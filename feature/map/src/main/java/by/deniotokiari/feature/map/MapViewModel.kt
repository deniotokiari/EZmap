package by.deniotokiari.feature.map

import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.deniotokiari.utils.android.get
import by.deniotokiari.utils.android.set

private const val KEY_ZOOM_LEVEL = "KEY_ZOOM_LEVEL"
private const val KEY_MAP_LOCATION = "KEY_MAP_LOCATION"
private const val DEFAULT_ZOOM_LEVEL = 3.0
private const val FOCUSED_ZOOM_LEVEL = 14.0
private const val MAX_ZOOM_LEVEL = 18.0

class MapViewModel(
    private val resources: Resources,
    private val preferences: SharedPreferences,
    val locationLiveData: LiveData<Location>
) : ViewModel() {

    val zoomLevel: LiveData<Double> = MediatorLiveData<Double>().apply {
        value = preferences[KEY_ZOOM_LEVEL, DEFAULT_ZOOM_LEVEL]

        addSource(locationLiveData) {
            if (value == DEFAULT_ZOOM_LEVEL) {
                value = FOCUSED_ZOOM_LEVEL
            }

            removeSource(locationLiveData)
        }
    }
    val mapLocation: LiveData<Location> = MediatorLiveData<Location>().apply {
        preferences.location()?.let { value = it }

        addSource(locationLiveData) {
            if (value == null) {
                value = it
            }

            removeSource(locationLiveData)
        }
    }

    private val _maxZoomLevel: MutableLiveData<Double> = MutableLiveData(MAX_ZOOM_LEVEL)
    val maxZoomLevel: LiveData<Double> by this::_maxZoomLevel

    private val _minZoomLevel: MutableLiveData<Double> = MutableLiveData()
    val minZoomLevel: LiveData<Double> by this::_minZoomLevel

    init {
        updateMinZoomLevel()
    }

    fun updateMinZoomLevel() {
        _minZoomLevel.value = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            1.5220123826976608
        } else {
            1.5680000000000476
        }
    }

    fun updateZoomLevel(zoomLevel: Double) {
        preferences[KEY_ZOOM_LEVEL] = zoomLevel
    }

    fun updateMapLocation(longitude: Double, latitude: Double) {
        preferences[KEY_MAP_LOCATION] = "$longitude $latitude"
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