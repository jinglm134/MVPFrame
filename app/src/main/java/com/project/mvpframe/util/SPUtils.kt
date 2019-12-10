package com.project.mvpframe.util

import android.content.Context
import android.content.SharedPreferences


/**
 * SharePreferenceUtils
 * @CreateDate 2019/12/10 10:25
 * @Author jaylm
 */

class SPUtils {
    companion object {
        private var preferencesUtil: SPUtils? = null
        private lateinit var preferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        fun getInstance(context: Context, spName: String = "MVPFrame"): SPUtils {
            if (preferencesUtil == null) {
                synchronized(SPUtils::class.java) {
                    if (preferencesUtil == null) {
                        preferencesUtil = SPUtils()
                        preferences = context.applicationContext.getSharedPreferences(
                            spName,
                            Context.MODE_PRIVATE
                        )
                        editor = preferences.edit()
                        editor.apply()
                    }
                }
            }
            return preferencesUtil!!
        }
    }

    /**
     * 保存数据 , 所有的类型都适用
     *
     * @param key key
     * @param param T
     */
    @Synchronized
    fun <T> saveParam(key: String, param: T) {
        // 得到object的类型
        when (param) {
            is String -> // 保存String 类型
                editor.putString(key, param)
            is Int -> // 保存integer 类型
                editor.putInt(key, param)
            is Boolean -> // 保存 boolean 类型
                editor.putBoolean(key, param)
            is Float -> // 保存float类型
                editor.putFloat(key, param)
            is Long -> // 保存long类型
                editor.putLong(key, param)
            else -> {
                // 不是基本类型则是保存对象,对象必须序列化
//                val bos = ByteArrayOutputStream()
//                try {
//                    val oos = ObjectOutputStream(bos)
//                    oos.writeObject(param)
//                    val productBase64 = Base64.encodeToString(
//                        bos.toByteArray(), Base64.DEFAULT
//                    )
//                    editor.putString(key, productBase64)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
            }
        }
        editor.apply()
    }


    /**
     * 得到保存数据的方法，所有类型都适用
     *
     * @param key key
     * @param defaultParam  T
     * @return T
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getParam(key: String, defaultParam: T): T? {
        return when (defaultParam) {
            is String -> preferences.getString(key, defaultParam) as T
            is Int -> preferences.getInt(key, (defaultParam)) as T
            is Boolean -> preferences.getBoolean(key, (defaultParam)) as T
            is Float -> preferences.getFloat(key, (defaultParam)) as T
            is Long -> preferences.getLong(key, (defaultParam)) as T
            else -> null
//            else -> {
//                val wordBase64 = preferences.getString(key, "")
//                val base64 = Base64.decode(wordBase64!!.toByteArray(), Base64.DEFAULT)
//                val bas = ByteArrayInputStream(base64)
//                try {
//                    val bis = ObjectInputStream(bas)
//                    val data = bis.readObject()
//                    data as T
//                } catch (e: Exception) {
//                }
//                null
//            }
        }
    }


    /**
     * SP中移除该key
     * @param key 键
     */
    @Synchronized
    fun remove(key: String) {
        editor.remove(key).apply()
    }


    /**
     * SP中清除所有数据
     */
    @Synchronized
    fun clear() {
        editor.clear().apply()
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
