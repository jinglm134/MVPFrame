package com.project.mvpframe.net

import android.net.ParseException
import org.json.JSONException
import retrofit2.adapter.rxjava2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 网络异常处理
 * @CreateDate 2019/12/2 15:22
 * @Author jaylm
 */
class RxException {

    companion object {
        fun exceptionHandler(e: Throwable): String {

            return when (e) {
                is UnknownHostException -> "找不到服务器地址"
                is SocketTimeoutException -> "请求网络超时"
                is HttpException -> convertStatusCode(e)
                is ParseException, is JSONException -> "数据解析错误"
                else -> "未知错误"
            }
        }

        private fun convertStatusCode(httpException: HttpException): String {
            return when {
                httpException.code() in 500..599 -> "服务器处理请求出错"
                httpException.code() in 400..499 -> "服务器无法处理请求"
                httpException.code() in 300..399 -> "请求被重定向到其他页面"
                else -> httpException.message()
            }
        }
    }
}
