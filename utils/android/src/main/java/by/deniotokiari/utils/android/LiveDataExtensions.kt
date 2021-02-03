package by.deniotokiari.utils.android

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import by.deniotokiari.utils.kotlin.Consumable

fun <T> LiveData<Consumable<T>>.consume(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    observe(lifecycleOwner) {
        it.consume(block)
    }
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, block: (T) -> Unit) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            block(t)

            removeObserver(this)
        }
    })

}