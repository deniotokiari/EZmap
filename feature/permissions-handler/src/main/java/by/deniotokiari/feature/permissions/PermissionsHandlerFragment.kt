package by.deniotokiari.feature.permissions

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.deniotokiari.core.navigation.MainNavigation

class PermissionsHandlerFragment(
    private val mainNavigation: MainNavigation
) : Fragment(R.layout.fragment_permissions_handler) {

    private var activityResultLauncher: ActivityResultLauncher<Array<String>>? = null
    private val permissions: Array<String> by lazy {
        requireContext().packageManager.getPackageInfo(requireContext().packageName, PackageManager.GET_PERMISSIONS).requestedPermissions
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityResultLauncher = requireActivity().registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            when {
                result.values.all { it } -> mainNavigation.permissionsHandlerToMap()
                shouldShowRequestPermissionRationale() -> {
                    AlertDialog
                        .Builder(requireContext())
                        .setCancelable(false)
                        .setMessage(R.string.permissions_request)
                        .setPositiveButton(R.string.ok) { _, _ -> activityResultLauncher?.launch(permissions) }
                        .show()
                }
                else -> {
                    AlertDialog
                        .Builder(requireContext())
                        .setCancelable(false)
                        .setMessage(R.string.permissions_request)
                        .setPositiveButton(R.string.settings) { _, _ ->
                            startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply { data = Uri.fromParts("package", requireActivity().packageName, null) })
                        }
                        .setNegativeButton(R.string.no_thanks) { _, _ -> requireActivity().finishAffinity() }
                        .show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        when {
            permissions.map { ContextCompat.checkSelfPermission(requireContext(), it) }.all { it == PackageManager.PERMISSION_GRANTED } -> mainNavigation.permissionsHandlerToMap()
            shouldShowRequestPermissionRationale() -> {
                AlertDialog
                    .Builder(requireContext())
                    .setCancelable(false)
                    .setMessage(R.string.permissions_request)
                    .setPositiveButton(R.string.ok) { _, _ -> activityResultLauncher?.launch(permissions) }
                    .show()
            }
            else -> activityResultLauncher?.launch(permissions)
        }
    }

    private fun shouldShowRequestPermissionRationale() = permissions.map { shouldShowRequestPermissionRationale(it) }.any { it }
}