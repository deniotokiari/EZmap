package by.deniotokiari.ezmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import by.deniotokiari.core.navigation.MainNavigation
import by.deniotokiari.feature.permissions.PermissionsHandlerFragmentDirections
import by.deniotokiari.utils.kotlin.Consumable

class MainActivityViewModel : ViewModel(), MainNavigation {

    private val _navigation: MutableLiveData<Consumable<NavDirections>> = MutableLiveData()
    val navigation: LiveData<Consumable<NavDirections>> get() = _navigation

    override fun permissionsHandlerToMap() {
        _navigation.value = Consumable(PermissionsHandlerFragmentDirections.permissionsToMap())
    }
}