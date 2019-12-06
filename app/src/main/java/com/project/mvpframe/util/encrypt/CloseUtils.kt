package com.project.mvpframe.util.encrypt

import java.io.Closeable
import java.io.IOException

/**
 * @CreateDate 2019/12/6 9:30
 * @Author jaylm
 */
object CloseUtils {
    fun closeIO(vararg closeables: Closeable?) {
        for (closeable in closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}