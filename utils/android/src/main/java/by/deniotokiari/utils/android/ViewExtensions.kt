package by.deniotokiari.utils.android

import android.view.View
import androidx.core.view.ViewCompat
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Suppress("EXPERIMENTAL_API_USAGE")
suspend fun View.getStatusBarHeight(): Int {
    return suspendCancellableCoroutine {
        ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
            if (!it.isCompleted) {
                it.resume(insets.systemWindowInsetTop)
            }

            ViewCompat.setOnApplyWindowInsetsListener(this, null)

            insets
        }
    }
}