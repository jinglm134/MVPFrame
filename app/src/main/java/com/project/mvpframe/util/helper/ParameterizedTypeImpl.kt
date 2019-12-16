package com.project.mvpframe.util.helper

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @CreateDate 2019/12/16 15:08
 * @Author jaylm
 */
class ParameterizedTypeImpl(
    private val raw: Class<*>,
    private val args: Array<Type>,
    private val owner: Type
) : ParameterizedType {

    override fun getActualTypeArguments(): Array<Type> {
        return args
    }

    override fun getRawType(): Type {
        return raw
    }

    override fun getOwnerType(): Type? {
        return owner
    }
}