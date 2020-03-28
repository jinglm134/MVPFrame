package com.project.mvpframe.util

import android.os.Environment
import android.text.TextUtils
import com.project.mvpframe.app.MvpApp
import com.project.mvpframe.util.encrypt.CloseUtils
import java.io.*

/**
 * @CreateDate 2020/3/28 10:33
 * @Author jaylm
 */
object FileUtils {

    /**
     * 根据文件路径获取文件
     * @param filePath 文件路径
     * @return 文件
     */
    fun getFileByPath(filePath: String): File? {
        return if (TextUtils.isEmpty(filePath)) null else File(filePath)
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true: 存在 false: 不存在
     */
    fun isFileExists(filePath: String): Boolean {
        return isFileExists(getFileByPath(filePath))
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return true: 存在 false: 不存在
     */
    fun isFileExists(file: File?): Boolean {
        return file != null && file.exists()
    }

    /**
     * 判断是否是目录
     *
     * @param dirPath 目录路径
     * @return true: 是 false: 否
     */
    fun isDir(dirPath: String): Boolean {
        return isDir(getFileByPath(dirPath))
    }

    /**
     * 判断是否是目录
     *
     * @param file 文件
     * @return true: 是 false: 否
     */
    fun isDir(file: File?): Boolean {
        return isFileExists(file) && file!!.isDirectory
    }

    /**
     * 判断是否是文件
     *
     * @param filePath 文件路径
     * @return true: 是 false: 否
     */
    fun isFile(filePath: String): Boolean {
        return isFile(getFileByPath(filePath))
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件
     * @return true: 是false: 否
     */
    fun isFile(file: File?): Boolean {
        return isFileExists(file) && file!!.isFile
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 目录路径
     * @return true: 存在或创建成功  false: 不存在或创建失败
     */
    fun createOrExistsDir(dirPath: String): Boolean {
        return createOrExistsDir(getFileByPath(dirPath))
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return true: 存在或创建成功  false: 不存在或创建失败
     */
    private fun createOrExistsDir(file: File?): Boolean {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return true: 存在或创建成功 false: 不存在或创建失败
     */
    fun createOrExistsFile(filePath: String): Boolean {
        return createOrExistsFile(getFileByPath(filePath))
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return true: 存在或创建成功 false: 不存在或创建失败
     */
    fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile
        if (!createOrExistsDir(file.parentFile)) return false
        return try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }

    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param filePath 文件路径
     * @return true: 创建成功 false: 创建失败
     */
    fun createFileByDeleteOldFile(filePath: String): Boolean {
        return createFileByDeleteOldFile(getFileByPath(filePath))
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param file 文件
     * @return true: 创建成功 false: 创建失败
     */
    private fun createFileByDeleteOldFile(file: File?): Boolean {
        if (file == null) return false
        // 文件存在并且删除失败返回false
        if (file.exists() && file.isFile && !file.delete()) return false
        // 创建目录失败返回false
        if (!createOrExistsDir(file.parentFile)) return false
        return try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }

    }

    /**
     * 删除文件
     *
     * @param srcFilePath 文件路径
     * @return true: 删除成功 false: 删除失败
     */
    fun deleteFile(srcFilePath: String): Boolean {
        return deleteFile(getFileByPath(srcFilePath))
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return true: 删除成功 false: 删除失败
     */
    private fun deleteFile(file: File?): Boolean {
        return file != null && (!file.exists() || file.isFile && file.delete())
    }


    /**
     * 将字符串写入文件
     *
     * @param filePath 文件路径
     * @param content  写入内容
     * @param append   是否追加在文件末
     * @return true: 写入成功  false: 写入失败
     */
    fun writeFileFromString(filePath: String, content: String, append: Boolean): Boolean {
        return writeFileFromString(getFileByPath(filePath), content, append)
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 写入内容
     * @param append  是否追加在文件末
     * @return true: 写入成功  false: 写入失败
     */
    private fun writeFileFromString(file: File?, content: String?, append: Boolean): Boolean {
        if (file == null || content == null) return false
        if (!createOrExistsFile(file)) return false
        var bw: BufferedWriter? = null
        return try {
            bw = BufferedWriter(FileWriter(file, append))
            bw.write(content)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } finally {
            CloseUtils.closeIO(bw)
        }
    }


    /**
     * 简单获取文件编码格式
     *
     * @param filePath 文件路径
     * @return 文件编码
     */
    fun getFileCharsetSimple(filePath: String): String {
        return getFileCharsetSimple(getFileByPath(filePath))
    }

    /**
     * 简单获取文件编码格式
     *
     * @param file 文件
     * @return 文件编码
     */
    fun getFileCharsetSimple(file: File?): String {
        var p = 0
        var inputStream: InputStream? = null
        try {
            inputStream = BufferedInputStream(FileInputStream(file!!))
            p = (inputStream.read() shl 8) + inputStream.read()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            CloseUtils.closeIO(inputStream)
        }
        return when (p) {
            0xefbb -> "UTF-8"
            0xfffe -> "Unicode"
            0xfeff -> "UTF-16BE"
            else -> "GBK"
        }
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param file 文件
     * @return 文件拓展名
     */
    fun getFileExtension(file: File?): String? {
        return if (file == null) null else getFileExtension(file.path)
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param filePath 文件路径
     * @return 文件拓展名
     */
    fun getFileExtension(filePath: String): String {
        if (TextUtils.isEmpty(filePath)) return filePath
        val lastPoi = filePath.lastIndexOf('.')
        val lastSep = filePath.lastIndexOf(File.separator)
        return if (lastPoi == -1 || lastSep >= lastPoi) "" else filePath.substring(lastPoi + 1)
    }


    /**
     * 判断外部储存是否可用
     * @return true : 可用 false : 不可用
     */
    fun isExternalEnable(): Boolean {
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    }

    /**
     * 尽量使用外部存储,获取应用内的file路径
     * 该路径下的内容随app卸载而丢失
     */
    fun getFilePath(folder: String = ""): String {
        return if (isExternalEnable()) {
            //            /storage/emulated/0/Android/data/com.project.mvpframe/files/folder/
            if (TextUtils.isEmpty(folder)) {
                MvpApp.instance.getExternalFilesDir("")!!.path + File.separator
            } else {
                MvpApp.instance.getExternalFilesDir("")!!.path + File.separator + folder + File.separator
            }
        } else {
            //            /data/user/0/com.project.mvpframe/files/folder/
            if (TextUtils.isEmpty(folder)) {
                MvpApp.instance.filesDir.path + File.separator
            } else {
                MvpApp.instance.filesDir.path + File.separator + folder + File.separator
            }

        }
    }

    /**
     * 获取相册路径
     */
    fun getCameraPath(folder: String = ""): String {
        //        /storage/emulated/0/DCIM/Camera/folder/
        return if (TextUtils.isEmpty(folder)) {
            Environment.getExternalStorageDirectory().path + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator
        } else {
            Environment.getExternalStorageDirectory().path + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator + folder + File.separator
        }
    }

    /**
     * 尽量使用外部存储,获取应用外的data路径
     * 该路径下的内容app卸载也不会丢失
     */
    private fun getDataPath(): String {
        return if (isExternalEnable()) {
            //            /storage/emulated/0/data/
            Environment.getExternalStorageDirectory().path + File.separator + "data" + File.separator
        } else {
            //            /data/data/
            Environment.getDataDirectory().path + File.separator + "data" + File.separator
        }
    }
}