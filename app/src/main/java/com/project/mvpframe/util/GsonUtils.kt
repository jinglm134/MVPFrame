package com.project.mvpframe.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

/**
 * Gson解析
 * @CreateDate 2019/12/6 11:28
 * @Author jaylm
 */
object GsonUtils {

    //将jsonString转化成对象
    fun <T> parseJsonWithGson(jsonData: String, type: Class<T>): T {
        return Gson().fromJson(jsonData, type)
    }

    //将jsonString转化成集合
    fun <T> parseJsonArrayWithGson(jsonData: String): ArrayList<T> {
        return Gson().fromJson<ArrayList<T>>(jsonData, object : TypeToken<ArrayList<T>>() {}.type)
    }

    //获取assess中的文件
    fun getJson(context: Context, fileName: String): String {
        //将json数据变成字符串
        val stringBuilder = StringBuilder()
        try {
            //获取assets资源管理器
            val assetManager = context.assets
            //通过管理器打开文件并读取
            val bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
            var line: String
            while (true) {
                line = bf.readLine() ?: break
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }
}