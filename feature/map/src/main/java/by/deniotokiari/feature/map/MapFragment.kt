package by.deniotokiari.feature.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import by.deniotokiari.feature.map.databinding.FragmentMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : Fragment(R.layout.fragment_map) {

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

            addMapListener(object : MapListener {
                override fun onScroll(event: ScrollEvent?): Boolean {
                    return false
                }

                override fun onZoom(event: ZoomEvent?): Boolean {
                    if (boundingBox.latNorth == MapView.getTileSystem().maxLatitude && boundingBox.latSouth == MapView.getTileSystem().minLatitude) {
                        event?.zoomLevel?.let { minZoomLevel = it }
                    }

                    if (boundingBox.lonWest == MapView.getTileSystem().minLongitude && boundingBox.lonEast == MapView.getTileSystem().maxLongitude) {
                        event?.zoomLevel?.let { minZoomLevel = it }
                    }

                    return false
                }

            })

            setScrollableAreaLimitLatitude(MapView.getTileSystem().maxLatitude, MapView.getTileSystem().minLatitude, 0)
            setScrollableAreaLimitLongitude(MapView.getTileSystem().minLongitude, MapView.getTileSystem().maxLongitude, 0)

            controller.setZoom(14.0)

            val gpsMyLocationProvider = GpsMyLocationProvider(requireContext())
            val myLocationNewOverlay = MyLocationNewOverlay(gpsMyLocationProvider, this)
            myLocationNewOverlay.enableMyLocation()
            myLocationNewOverlay.enableFollowLocation()

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