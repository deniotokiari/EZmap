package by.deniotokiari.feature.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import by.deniotokiari.core.tiles.provider.FilesViewModel
import by.deniotokiari.feature.map.databinding.FragmentMapBinding
import by.deniotokiari.utils.android.getDouble
import by.deniotokiari.utils.android.getStatusBarHeight
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.mapsforge.MapsForgeTileProvider
import org.osmdroid.mapsforge.MapsForgeTileSource
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ScaleBarOverlay

class MapFragment(
    private val filesViewModel: FilesViewModel
) : Fragment(R.layout.fragment_map) {

    private val binding: FragmentMapBinding by lazy { FragmentMapBinding.bind(requireView()) }
    private val viewModel: MapViewModel by viewModel()
    private val gpsMyLocationProvider = LocationProvider()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.map) {
            MapsForgeTileSource.createInstance(requireActivity().application)

            filesViewModel.files.observe(viewLifecycleOwner) {
                val fromFiles = MapsForgeTileSource.createFromFiles(it.toTypedArray())
                val forge = MapsForgeTileProvider(SimpleRegisterReceiver(context), fromFiles, null)
                tileProvider = forge
            }

            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            setMultiTouchControls(true)

            isHorizontalMapRepetitionEnabled = false
            isVerticalMapRepetitionEnabled = false

            minZoomLevel = resources.getDouble(R.string.map_min_zoom_level)
            maxZoomLevel = resources.getDouble(R.string.map_max_zoom_level)

            setScrollableAreaLimitLatitude(MapView.getTileSystem().maxLatitude, MapView.getTileSystem().minLatitude, 0)
            setScrollableAreaLimitLongitude(MapView.getTileSystem().minLongitude, MapView.getTileSystem().maxLongitude, 0)

            val myLocationNewOverlay = MyLocationOverlay(this, gpsMyLocationProvider)
            val scaleBarOverlay = ScaleBarOverlay(this).apply {
                viewModel.viewModelScope.launch {
                    setScaleBarOffset(10, requireActivity().window.decorView.getStatusBarHeight() + 10)
                }

                setEnableAdjustLength(true)
            }

            overlays.add(scaleBarOverlay)
            overlays.add(myLocationNewOverlay)
        }

        bindViewModel()

        binding.myLocation.setOnClickListener {
            viewModel.moveToCurrentLocation()
        }

        binding.zoomIn.setOnClickListener {
            viewModel.zoomIn()
        }
        binding.zoomOut.setOnClickListener {
            viewModel.zoomOut()
        }
    }

    private fun bindViewModel() {
        viewModel.locationLiveData.observe(viewLifecycleOwner, gpsMyLocationProvider::onLocationChanged)
        viewModel.zoomLevel.observe(viewLifecycleOwner) {
            binding.map.controller.zoomTo(it, 300L)
        }
        viewModel.mapLocation.observe(viewLifecycleOwner) {
            binding.map.controller.animateTo(GeoPoint(it), null, 300L)
        }
    }

    override fun onResume() {
        super.onResume()

        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()

        binding.map.onPause()
        viewModel.saveZoomLevel(binding.map.zoomLevelDouble)
        viewModel.saveMapLocation(binding.map.mapCenter.longitude, binding.map.mapCenter.latitude)
    }
}