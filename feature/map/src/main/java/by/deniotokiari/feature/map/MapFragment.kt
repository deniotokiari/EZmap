package by.deniotokiari.feature.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.deniotokiari.feature.map.databinding.FragmentMapBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment() : Fragment(R.layout.fragment_map) {

    private val binding: FragmentMapBinding by lazy { FragmentMapBinding.bind(requireView()) }
    private val viewModel: MapViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.title.observe(viewLifecycleOwner) {
            binding.title.text = it
        }
    }
}