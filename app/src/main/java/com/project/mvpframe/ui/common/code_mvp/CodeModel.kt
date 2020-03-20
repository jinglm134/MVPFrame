package com.project.mvpframe.ui.common.code_mvp

import com.project.mvpframe.base.BaseModel
import com.project.mvpframe.bean.BaseResponse
import com.project.mvpframe.net.ApiService
import com.project.mvpframe.net.RetrofitManager
import com.project.mvpframe.util.encrypt.EncryptUtils
import io.reactivex.Observable

/**
 * @CreateDate 2019/12/19 14:54
 * @Author jaylm
 */
open class CodeModel : BaseModel() {
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