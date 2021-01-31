package by.deniotokiari.feature.permissions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.deniotokiari.core.navigation.MainNavigation

class PermissionsHandlerFragment(
    private val mainNavigation: MainNavigation
): Fragment(R.layout.fragment_permissions_handler) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.button).setOnClickListener { mainNavigation.permissionsHandlerToMap() }
    }
}