package by.deniotokiari.ezmap

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(
    resources: Resources
) : ViewModel() {

    private val _text: MutableLiveData<String> = MutableLiveData(resources.getString(R.string.app_name))
    val text: LiveData<String> get() = _text

}