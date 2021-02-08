package by.deniotokiari.utils.android

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private inline fun <T> SharedPreferences.edit(key: String, value: T, operation: SharedPreferences.Editor.(String, T) -> Unit) {
    edit().also { it.operation(key, value) }.apply()
}

inline fun <T> SharedPreferences.delegate(
    defaultValue: T,
    key: String?,
    crossinline getter: SharedPreferences.(String, T) -> T,
    crossinline setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T> {

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = edit().setter(key ?: property.name, value).apply()

    override fun getValue(thisRef: Any, property: KProperty<*>): T = getter(key ?: property.name, defaultValue)
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> SharedPreferences.preference(defaultValue: T, key: String? = null): ReadWriteProperty<Any, T> = when (defaultValue) {
    is String -> delegate(defaultValue, key, SharedPreferences::getString, SharedPreferences.Editor::putString)
    is Double -> delegate(defaultValue, key, SharedPreferences::getDouble, SharedPreferences.Editor::putDouble)

    else -> throw UnsupportedOperationException()
} as ReadWriteProperty<Any, T>

fun SharedPreferences.getDouble(key: String, defaultValue: Double): Double {
    return requireNotNull(getString(key, defaultValue.toString())).toDouble()
}

fun SharedPreferences.Editor.putDouble(key: String, value: Double): SharedPreferences.Editor {
    return putString(key, value.toString())
}

operator fun <T> SharedPreferences.set(key: String, value: T) {
    when (value) {
        is String -> edit(key, value, SharedPreferences.Editor::putString)
        is Double -> edit(key, value, SharedPreferences.Editor::putDouble)

        else -> throw UnsupportedOperationException()
    }
}

@Suppress("UNCHECKED_CAST")
operator fun <T : Any> SharedPreferences.get(key: String, defaultValue: T): T {
    return when (defaultValue) {
        is String -> getString(key, defaultValue)
        is Double -> getDouble(key, defaultValue)

        else -> throw UnsupportedOperationException()
    } as T
}