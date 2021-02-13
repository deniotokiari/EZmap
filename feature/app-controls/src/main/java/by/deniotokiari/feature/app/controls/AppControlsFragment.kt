package by.deniotokiari.feature.app.controls

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import by.deniotokiari.core.navigation.MainNavigation
import by.deniotokiari.feature.app.controls.databinding.FragmentAppControlsBinding

class AppControlsFragment(
    private val navigation: MainNavigation
) : Fragment(R.layout.fragment_app_controls) {

    private val binding: FragmentAppControlsBinding by lazy { FragmentAppControlsBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolTipsForMapControls()

        binding.menu.setOnClickListener {
            navigation.openMenu()
        }
    }

    private fun initToolTipsForMapControls() {
        with(binding) { arrayOf(search, directions, mapLayers, bookmarks, menu) }.forEach { TooltipCompat.setTooltipText(it, it.contentDescription) }
    }
}