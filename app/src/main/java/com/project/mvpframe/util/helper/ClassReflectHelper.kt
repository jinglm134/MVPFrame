package com.project.mvpframe.util.helper

import android.os.IInterface
import java.lang.reflect.ParameterizedType

/**
 * @CreateDate 2019/11/29 9:44
 * @Author jaylm
 */
object ClassReflectHelper {

    fun <T> getT(o: Any, i: Int): T {
        return ((o.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[i] as Class<T>)
            .newInstance()
    }

    fun forName(className: String): Class<*>? {
        try {
            return Class.forName(className)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return null
    }
}