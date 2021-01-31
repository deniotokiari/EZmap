package by.deniotokiari.utils.android

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import by.deniotokiari.utils.kotlin.Consumable

fun <T> LiveData<Consumable<T>>.consume(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    observe(lifecycleOwner) {
        it.consume(block)
    }
}