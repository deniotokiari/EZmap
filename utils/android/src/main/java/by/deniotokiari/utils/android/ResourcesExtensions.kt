package by.deniotokiari.utils.android

import android.content.res.Resources
import androidx.annotation.StringRes

fun Resources.getDouble(@StringRes id: Int): Double {
    return getString(id).toDouble()
}