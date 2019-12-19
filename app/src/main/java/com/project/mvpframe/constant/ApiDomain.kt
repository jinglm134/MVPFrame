package com.project.mvpframe.constant

import com.project.mvpframe.BuildConfig

/**
 * 请求域名
 * @CreateDate 2019/12/2 12:27
 * @Author jaylm
 */
class ApiDomain {
    companion object {
        const val BASE_URL: String = BuildConfig.BASEAPI//默认域名
        const val BASE_URL_BANNER: String = BuildConfig.BASEAPI_BANNER//BANNER域名
    }
}