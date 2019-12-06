package com.project.mvpframe.util.encrypt

import android.text.TextUtils
import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * @CreateDate 2019/12/6 9:27
 * @Author jaylm
 */
class Base64Utils {
    /**
     * Base64加密
     *
     * @param string 加密字符串
     * @return 加密结果字符串
     */
    fun base64EncodeStr(string: String): String {
        return if (TextUtils.isEmpty(string)) "" else Base64.encodeToString(
            string.toByteArray(),
            Base64.DEFAULT
        )
    }

    /**
     * Base64解密
     *
     * @param string 解密字符串
     * @return 解密结果字符串
     */
    fun base64DecodedStr(string: String): String {
        return if (TextUtils.isEmpty(string)) "" else String(Base64.decode(string, Base64.DEFAULT))
    }

    /**
     * Base64加密
     *
     * @param file 加密文件
     * @return 加密结果字符串
     */
    fun base64EncodeFile(file: File?): String {
        if (null == file) return ""

        var inputFile: FileInputStream? = null
        try {
            inputFile = FileInputStream(file)
            val buffer = ByteArray(file.length().toInt())
            inputFile.read(buffer)
            return Base64.encodeToString(buffer, Base64.DEFAULT)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            CloseUtils.close(inputFile)
        }
        return ""
    }

    /**
     * Base64解密
     *
     * @param filePath 解密文件路径
     * @param code     解密文件编码
     * @return 解密结果文件
     */
    fun base64DecodedFile(filePath: String, code: String): File? {
        if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(code)) {
            return null
        }

        var fos: FileOutputStream? = null
        val desFile = File(filePath)
        try {
            val decodeBytes = Base64.decode(code.toByteArray(), Base64.DEFAULT)
            fos = FileOutputStream(desFile)
            fos.write(decodeBytes)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            CloseUtils.close(fos)
        }
        return desFile
    }
}