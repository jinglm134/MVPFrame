package com.project.mvpframe.util.helper

import android.app.Activity
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @CreateDate 2019/3/7 13:44
 * @Author jaylm
 */
fun <U, T> bindExtra(key: String) = BindLoader<U, T>(key)

fun <U, T> bindArgument(key: String) = BindLoader<U, T>(key)

//fun <U, T> android.app.Fragment.bindArgument(key: String) = BindLoader<U, T>(key)

private class IntentDelegate<in U, out T>(private val key: String) : ReadOnlyProperty<U, T> {
    override fun getValue(thisRef: U, property: KProperty<*>): T {
        @Suppress("UNCHECKED_CAST")
        return when (thisRef) {
            is Fragment -> thisRef.arguments?.get(key) as T
            else -> (thisRef as Activity).intent?.extras?.get(key) as T
        }
    }
}

class BindLoader<in U, out T>(private val key: String) {
    operator fun provideDelegate(thisRef: U, prop: KProperty<*>): ReadOnlyProperty<U, T> {
        // 创建委托
        return IntentDelegate(key)
    }
}
