package com.project.mvpframe.net

import com.project.mvpframe.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.util.*

/**
 * 日志拦截器
 * @CreateDate 2019/12/2 14:24
 * @Author jaylm
 */
class LogInterceptor : Interceptor {

    private val mUTF8 = Charset.forName("UTF-8")
    private val mTAG = "OkHttpRequest"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        LogUtils.v(mTAG, "-----------------------------------------> START_REQUEST")
        LogUtils.v(mTAG, String.format(Locale.getDefault(), "requestUrl:$request"))
        val requestBody = request.body()
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)

            var charset: Charset = mUTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(mUTF8)!!
            }

            LogUtils.v(
                mTAG,
                String.format(Locale.getDefault(), "requestBody:${buffer.readString(charset)}")
            )
        }

        val t1 = System.nanoTime()
        val response = chain.proceed(chain.request())
        val t2 = System.nanoTime()
        LogUtils.v(
            mTAG,
            "Received response for ${response.request().url()} in ${(t2 - t1) / 1e6}ms"
        )

        val mediaType = response.body()!!.contentType()
        val content = response.body()!!.string()
        LogUtils.long(mTAG, "response body:$content")
        LogUtils.v(mTAG, "-----------------------------------------> END_RESPONSE\n")
        return response.newBuilder()
            .body(okhttp3.ResponseBody.create(mediaType, content))
            .build()
    }
}