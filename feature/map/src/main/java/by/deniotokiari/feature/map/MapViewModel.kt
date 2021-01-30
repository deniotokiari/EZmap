package by.deniotokiari.feature.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    private val _title: MutableLiveData<String> = MutableLiveData("Test map")
    val title: LiveData<String> get() = _title
}