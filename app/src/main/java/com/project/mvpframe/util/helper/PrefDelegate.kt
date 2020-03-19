package com.project.mvpframe.util.helper

import android.content.Context
import android.content.SharedPreferences
import com.project.mvpframe.app.MvpApp
import com.project.mvpframe.constant.SPConst
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @CreateDate 2020/3/7 16:23
 * @Author jaylm
 */
class PrefDelegate<T>(
    private val key: String,
    private val param: T,
    private val prefName: String = SPConst.APP_NAME
) : ReadWriteProperty<Any?, T> {
    private val prefs: SharedPreferences by lazy {
        MvpApp.instance.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getParam()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        saveParam()
    }


    /**
     * 保存数据 , 所有的基础类型都适用
     */
    @Synchronized
    private fun saveParam() = with(prefs.edit()) {
        when (param) {
            is String -> // 保存String 类型
                putString(key, param)
            is Int -> // 保存integer 类型
                putInt(key, param)
            is Boolean -> // 保存 boolean 类型
                putBoolean(key, param)
            is Float -> // 保存float类型
                putFloat(key, param)
            is Long -> // 保存long类型
                putLong(key, param)
            else -> throw IllegalArgumentException("Unknown Type.")
        }.apply()
    }


    /**
     * 得到保存数据的方法，所有基础类型都适用
     */
    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    private fun <T> getParam(): T {
        return when (param) {
            is String -> prefs.getString(key, param)
            is Int -> prefs.getInt(key, param)
            is Boolean -> prefs.getBoolean(key, param)
            is Float -> prefs.getFloat(key, param)
            is Long -> prefs.getLong(key, param)
            else -> throw IllegalArgumentException("Unknown Type")
        } as T
    }

}

