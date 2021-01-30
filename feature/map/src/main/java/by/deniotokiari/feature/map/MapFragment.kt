package by.deniotokiari.feature.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import by.deniotokiari.feature.map.databinding.FragmentMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding: FragmentMapBinding by lazy { FragmentMapBinding.bind(requireView()) }
    private val viewModel: MapViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolTipsForMapControls()
    }

    private fun initToolTipsForMapControls() {
        with(binding) { arrayOf(search, directions, mapLayers, bookmarks, menu) }.forEach { TooltipCompat.setTooltipText(it, it.contentDescription) }
    }
}