package com.project.mvpframe.net

import com.project.mvpframe.constant.ApiDomain
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * retrofit管理类
 * @CreateDate 2019/12/2 11:03
 * @Author jaylm
 */
class RetrofitManager {

    companion object {
        private const val DEFAULT_TIME = 7500L
        private var mInstance: RetrofitManager? = null
        private var mRetrofit: Retrofit? = null

        fun <T> getService(service: Class<T>): T {
            return getInstance().getRetrofit().create(service)
        }

        private fun getInstance(): RetrofitManager {
            if (mInstance == null) {
                synchronized(RetrofitManager::class.java) {
                    if (mInstance == null) {
                        mInstance = RetrofitManager()
                    }
                }
            }
            return mInstance!!
        }
    }


    private fun getRetrofit(): Retrofit {
        if (mRetrofit == null) {
            synchronized(RetrofitManager::class) {
                if (mRetrofit == null) {
                    //                    val logInterceptor = HttpLoggingInterceptor()
                    //                    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    //httpClient
                    val client = OkHttpClient().newBuilder()
                        .readTimeout(DEFAULT_TIME, TimeUnit.MILLISECONDS) //设置读取超时时间
                        .connectTimeout(DEFAULT_TIME, TimeUnit.MILLISECONDS) //设置请求超时时间
                        .writeTimeout(DEFAULT_TIME, TimeUnit.MILLISECONDS) //设置写入超时时间
                        .addInterceptor(HeaderInterceptor()) //网络拦截
                        .addInterceptor(LogInterceptor()) //打印拦截
                        .retryOnConnectionFailure(false) //设置出现错误进行重新连接。
                        .build()

                    // Retrofit
                    mRetrofit = Retrofit.Builder().client(client).baseUrl(ApiDomain.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(
                            Schedulers.io())).addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
        }
        return mRetrofit!!
    }

}