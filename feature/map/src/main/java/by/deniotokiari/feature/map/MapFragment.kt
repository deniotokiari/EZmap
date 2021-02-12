package by.deniotokiari.feature.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import by.deniotokiari.feature.map.databinding.FragmentMapBinding
import by.deniotokiari.utils.android.getDouble
import by.deniotokiari.utils.android.getStatusBarHeight
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.mapsforge.MapsForgeTileProvider
import org.osmdroid.mapsforge.MapsForgeTileSource
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver
import org.osmdroid.tileprovider.util.StorageUtils
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ScaleBarOverlay
import java.io.File

class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding: FragmentMapBinding by lazy { FragmentMapBinding.bind(requireView()) }
    private val viewModel: MapViewModel by viewModel()
    private val gpsMyLocationProvider = LocationProvider()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolTipsForMapControls()

        with(binding.map) {
            MapsForgeTileSource.createInstance(requireActivity().application)
            val fromFiles = MapsForgeTileSource.createFromFiles(findFiles())
            val forge = MapsForgeTileProvider(SimpleRegisterReceiver(context), fromFiles, null)

            tileProvider = forge

            //Configuration.getInstance().userAgentValue = context.packageName
            //setTileSource(TileSourceFactory.MAPNIK)

            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            setMultiTouchControls(true)
            isTilesScaledToDpi = true

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

    private fun initToolTipsForMapControls() {
        with(binding) { arrayOf(search, directions, mapLayers, bookmarks, menu) }.forEach { TooltipCompat.setTooltipText(it, it.contentDescription) }
    }

    private fun findFiles(): Array<File> {
        return StorageUtils
            .getStorageList(requireContext())
            .mapNotNull { File(it.path + File.separator).listFiles { file -> file.name.endsWith(".map") }?.toList() }
            .flatten()
            .toTypedArray()
    }
}