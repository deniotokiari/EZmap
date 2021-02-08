package by.deniotokiari.feature.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import by.deniotokiari.feature.map.databinding.FragmentMapBinding
import by.deniotokiari.utils.android.observeOnce
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ScaleBarOverlay

class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding: FragmentMapBinding by lazy { FragmentMapBinding.bind(requireView()) }
    private val viewModel: MapViewModel by viewModel()
    private val gpsMyLocationProvider = LocationProvider()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolTipsForMapControls()

        with(binding.map) {
            Configuration.getInstance().userAgentValue = context.packageName
            setTileSource(TileSourceFactory.MAPNIK)
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            setMultiTouchControls(true)
            isTilesScaledToDpi = true

            isHorizontalMapRepetitionEnabled = false
            isVerticalMapRepetitionEnabled = false

            setScrollableAreaLimitLatitude(MapView.getTileSystem().maxLatitude, MapView.getTileSystem().minLatitude, 0)
            setScrollableAreaLimitLongitude(MapView.getTileSystem().minLongitude, MapView.getTileSystem().maxLongitude, 0)

            val myLocationNewOverlay = MyLocationOverlay(this, gpsMyLocationProvider)
            val scaleBarOverlay = ScaleBarOverlay(this).apply {
                setScaleBarOffset(10, 10)
                setEnableAdjustLength(true)
            }

            overlays.add(scaleBarOverlay)
            overlays.add(myLocationNewOverlay)
        }

        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.minZoomLevel.observe(viewLifecycleOwner, binding.map::setMinZoomLevel)
        viewModel.maxZoomLevel.observe(viewLifecycleOwner, binding.map::setMaxZoomLevel)
        viewModel.locationLiveData.observe(viewLifecycleOwner, gpsMyLocationProvider::onLocationChanged)
        viewModel.zoomLevel.observe(viewLifecycleOwner, binding.map.controller::setZoom)
        viewModel.locationLiveData.observeOnce(viewLifecycleOwner) {
            //binding.map.controller.animateTo(GeoPoint(it), 14.0, null)
        }
        viewModel.mapLocation.observe(viewLifecycleOwner) {
            binding.map.controller.animateTo(GeoPoint(it))
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

    override fun onConfigurationChanged(newConfig: android.content.res.Configuration) {
        super.onConfigurationChanged(newConfig)

        viewModel.updateMinZoomLevel()
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.updateZoomLevel(binding.map.zoomLevelDouble)
        viewModel.updateMapLocation(binding.map.mapCenter.longitude, binding.map.mapCenter.latitude)
    }

    private fun initToolTipsForMapControls() {
        with(binding) { arrayOf(search, directions, mapLayers, bookmarks, menu) }.forEach { TooltipCompat.setTooltipText(it, it.contentDescription) }
    }
}