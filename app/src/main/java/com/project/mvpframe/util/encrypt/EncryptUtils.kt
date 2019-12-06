package com.project.mvpframe.util.encrypt

/**
 * @CreateDate 2019/12/6 16:33
 * @Author jaylm
 */
object EncryptUtils {
    fun encriptBody(str: String): String {
        return DESUtils.des(str, "ddsxcxvv")
    }
}