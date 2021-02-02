package by.deniotokiari.feature.map

import android.graphics.Bitmap
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import by.deniotokiari.feature.map.databinding.FragmentMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapFragment(
    private val locationLiveData: LiveData<Location>
) : Fragment(R.layout.fragment_map) {

    private val binding: FragmentMapBinding by lazy { FragmentMapBinding.bind(requireView()) }
    private val viewModel: MapViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolTipsForMapControls()

        with(binding.map) {
            Configuration.getInstance().userAgentValue = context.packageName
            setTileSource(TileSourceFactory.MAPNIK)
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            setMultiTouchControls(true)
            isTilesScaledToDpi = true

            maxZoomLevel = 18.0
            isHorizontalMapRepetitionEnabled = false
            isVerticalMapRepetitionEnabled = false

            minZoomLevel = when (context.resources.configuration.orientation) {
                android.content.res.Configuration.ORIENTATION_PORTRAIT -> 1.5220123826976608
                else -> 1.5680000000000476
            }

            setScrollableAreaLimitLatitude(MapView.getTileSystem().maxLatitude, MapView.getTileSystem().minLatitude, 0)
            setScrollableAreaLimitLongitude(MapView.getTileSystem().minLongitude, MapView.getTileSystem().maxLongitude, 0)

            controller.setZoom(3.0)

            val gpsMyLocationProvider = LocationProvider()

            locationLiveData.observe(viewLifecycleOwner) {
                gpsMyLocationProvider.onLocationChanged(it)
            }

            val myLocationNewOverlay = MyLocationNewOverlay(gpsMyLocationProvider, this)
            myLocationNewOverlay.setPersonIcon(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))

            myLocationNewOverlay.runOnFirstFix {
                post {
                    controller.animateTo(myLocationNewOverlay.myLocation, 14.0, null)
                }
            }

            val scaleBarOverlay = ScaleBarOverlay(this)
            scaleBarOverlay.setScaleBarOffset(10, 10)

            overlays.add(scaleBarOverlay)
            overlays.add(myLocationNewOverlay)
        }
    }

    override fun onResume() {
        super.onResume()

        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()

        binding.map.onPause()
    }

    private fun initToolTipsForMapControls() {
        with(binding) { arrayOf(search, directions, mapLayers, bookmarks, menu) }.forEach { TooltipCompat.setTooltipText(it, it.contentDescription) }
    }
}