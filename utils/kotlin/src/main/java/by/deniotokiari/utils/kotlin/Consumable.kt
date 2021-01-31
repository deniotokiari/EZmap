package by.deniotokiari.utils.kotlin

class Consumable<T>(private var data: T? = null) {

    fun consume(block: (T) -> Unit) {
        data?.let(block)
        data = null
    }
}