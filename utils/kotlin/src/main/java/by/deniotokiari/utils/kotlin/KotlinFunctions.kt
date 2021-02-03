package by.deniotokiari.utils.kotlin

fun <A, B> onNotNull(a: A?, b: B?, block: (A, B) -> Unit) {
    if (a != null && b != null) {
        block(a, b)
    }
}