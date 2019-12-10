package com.project.mvpframe.net

import android.util.Log
import com.project.mvpframe.constant.ApiDomain
import okhttp3.HttpUrl
import okhttp3.Interceptor
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
            val newBaseUrl = if ("other" == headerValue) {
                HttpUrl.parse(ApiDomain.BASE_URL_OTHER)
            } else {
                oldHttpUrl
            }
            //在oldHttpUrl的基础上重建新的HttpUrl，修改需要修改的url部分
            val newFullUrl = oldHttpUrl
                .newBuilder()
                .scheme(newBaseUrl!!.scheme())//更换网络协议,根据实际情况更换成https或者http
                .host(newBaseUrl.host())//更换主机名
                .port(newBaseUrl.port())//更换端口
//                .removePathSegment(0)//移除第一个参数v1
                .build()
            //重建这个request，通过builder.url(newFullUrl).build()；
            // 然后返回一个response至此结束修改
            Log.e("Url", "intercept: $newFullUrl")
            request = builder.url(newFullUrl).build()
        }

        val requestBuilder = request.newBuilder()
            .addHeader("deviceType", "app")
            .addHeader("userId", "35881a46eada4ade9ebb7a1675b4ce97")
            .addHeader(
                "Authorization",
                "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiY2xpZW50MiIsImNsaWVudDEiXSwidXNlcl9uYW1lIjoiMTgwMzAwMDAwMDAiLCJzY29wZSI6WyJhcHAiXSwiZXhwIjoxNTc1NjIwNzM1LCJ1c2VySWQiOiIzNTg4MWE0NmVhZGE0YWRlOWViYjdhMTY3NWI0Y2U5NyIsImp0aSI6IjQwMjQ5YmVhLTE5OWMtNGY5Yi05ZGJkLTU1ODhkYzBlMzJiOCIsImNsaWVudF9pZCI6ImNsaWVudDIifQ.kdbxEpuveNQkRSL5xgxZ0LdT0eIOraxG8L0BtYhpXlLuOHYrcgMAw9lujHqissSNjNckX1rRzHSZpdhArA8FWoCR-ntl83LMeisKJ9edIdp6zZ8DeZqLA7Aq_lWsL5lFafWzNCJBT0zPxuLsuCrU44jD290zR8bJaKsH3FHos3qhO7pOgyLMu66io8glVjwXdK-kllhgU--ZlQEdGBrQP5eknQiMX_FYFxe8-lyCrMwPwjfJlMhJCQmbAwE0QVO1ANwVXZMnDaQIB1052ia70O_1CYNb3gfRjalcv3lbsd5S_u27dJm4K2JgtQypx1tdDs6W3Az4Ib2vH4XICYbuWA"
            )
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .method(request.method(), request.body())
            .build()

        return chain.proceed(requestBuilder)
    }
}