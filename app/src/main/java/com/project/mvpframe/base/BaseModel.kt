package com.project.mvpframe.base

import com.project.mvpframe.bean.BaseResponse
import com.project.mvpframe.net.ApiService
import com.project.mvpframe.net.RetrofitManager
import com.project.mvpframe.util.encrypt.EncryptUtils
import io.reactivex.Observable

/**
 * @CreateDate 2019/11/29 9:41
 * @Author jaylm
 */
open class BaseModel {
    fun getCode(
        mobile: String,
        noteType: String
    ): Observable<BaseResponse<String>> {
        val map = HashMap<String, Any>()
        map["mobile"] = mobile
        map["noteType"] = noteType
        val encrypt = EncryptUtils.encrypt(map)
        return RetrofitManager.getService(ApiService::class.java)
            .getCode(encrypt)
    }


}