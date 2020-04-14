package com.project.mvpframe.net

import com.project.mvpframe.constant.ApiDomain
import com.project.mvpframe.constant.SPConst
import com.project.mvpframe.util.LogUtils
import com.project.mvpframe.util.SPUtils
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 请求拦截
 * @CreateDate 2019/12/2 13:37
 * @Author jaylm
 */
class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        var request = chain.request()
        //从request中获取原有的HttpUrl实例oldHttpUrl
        val oldHttpUrl = request.url()
        //获取request的创建者builder
        val builder = request.newBuilder()
        //从request中获取headers，通过给定的键url_name
        val headerValues: List<String> = request.headers("url_name")
        if (headerValues.isNotEmpty()) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader("url_name")
            //匹配获得新的BaseUrl
            val headerValue = headerValues[0]
            val newBaseUrl = if ("banner" == headerValue) {
                HttpUrl.parse(ApiDomain.BASE_URL_BANNER)
            } else {
                oldHttpUrl
            }
            //在oldHttpUrl的基础上重建新的HttpUrl，修改需要修改的url部分
            val newFullUrl = oldHttpUrl
                .newBuilder()
                .scheme(newBaseUrl!!.scheme())//更换新url的网络协议,根据实际情况更换成https或者http
                .host(newBaseUrl.host())//更换新url的主机名
                .port(newBaseUrl.port())//更换新url的端口
//                .addEncodedPathSegment(newBaseUrl.encodedPath())
//                .removePathSegment(0)//移除第一个参数v1
                .build()
            // 然后返回一个response至此结束修改
            LogUtils.v("HeaderInterceptor", "url_intercept = $newFullUrl")
            request = builder.url(newFullUrl).build()
        }

        val url = request.url()
        var requestBuilder: Request.Builder = request.newBuilder()
        if (url.toString().contains("strict")) {
            requestBuilder = requestBuilder.header(
                "userId",//header 为setHeader,即userId唯一
                SPUtils.getParam(SPConst.SP_USER_ID, "")
            ).header(
                "Authorization",//header 为setHeader,即Authorization唯一
                "Bearer${SPUtils.getParam(SPConst.SP_TOKEN, "")}"
            )
        }
        val build = requestBuilder.method(request.method(), request.body())
            .addHeader("deviceType", "app")
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .build()

        return chain.proceed(build)
    }
}