package by.deniotokiari.feature.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.deniotokiari.core.navigation.MainNavigation

class PermissionsHandlerFragment(
    private val mainNavigation: MainNavigation
) : Fragment(R.layout.fragment_permissions_handler) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (result.values.all { it }) {
                mainNavigation.permissionsHandlerToMap()
            } else {
                // TODO open settings
            }
        }.apply {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET
            )

            when {
                permissions.map { ContextCompat.checkSelfPermission(requireContext(), it) }.all { it == PackageManager.PERMISSION_GRANTED } -> mainNavigation.permissionsHandlerToMap()
                permissions.map { shouldShowRequestPermissionRationale(it) }.any { it } -> {
                    // TODO show dialog
                }
                else -> launch(permissions)
            }
        }
    }
}