package com.project.mvpframe.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.project.mvpframe.app.MvpApp
import com.project.mvpframe.constant.SPConst


/**
 * SharePreferenceUtils
 * @CreateDate 2019/12/10 10:25
 * @Author jaylm
 */

object SPUtils {

    private val preferences: SharedPreferences by lazy {
        MvpApp.context.getSharedPreferences(SPConst.APP_NAME, Context.MODE_PRIVATE)
    }

    /**
     * 保存数据
     *
     * @param key key
     * @param param T
     */
    @Synchronized
    fun <T> saveParam(key: String, param: T) = with(preferences.edit()) {
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
     * 保存数据 , 引用类型
     */
    @Synchronized
    fun <T> saveData(key: String, param: T) {
        val jsonString = Gson().toJson(param)
        preferences.edit().putString(key, jsonString).apply()
    }


    /**
     * 获取数据
     *
     * @param key key
     * @param defaultParam  T
     * @return T
     */

    @Suppress("UNCHECKED_CAST")
    fun <T> getParam(key: String, defaultParam: T): T {
        return when (defaultParam) {
            is String -> preferences.getString(key, defaultParam) as T
            is Int -> preferences.getInt(key, defaultParam) as T
            is Boolean -> preferences.getBoolean(key, defaultParam) as T
            is Float -> preferences.getFloat(key, defaultParam) as T
            is Long -> preferences.getLong(key, defaultParam) as T
            else -> defaultParam
        }
    }

    /**
     * 得到保存数据的方法，引用类型
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getData(key: String, type: Class<T>): T? {
        val jsonString = preferences.getString(key, "")
        return GsonUtils.parseJsonWithGson(jsonString!!, type)
    }


    /**
     * SP中移除该key
     * @param key 键
     */
    @Synchronized
    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }


    /**
     * SP中清除所有数据
     */
    @Synchronized
    fun clear() {
        preferences.edit().clear().apply()
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return true: 存在 false: 不存在
     */
    operator fun contains(key: String): Boolean {
        return preferences.contains(key)
    }
}
