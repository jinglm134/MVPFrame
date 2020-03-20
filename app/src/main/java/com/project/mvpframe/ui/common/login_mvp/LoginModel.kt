package com.project.mvpframe.ui.common.login_mvp

import android.text.TextUtils
import com.project.mvpframe.net.ApiService
import com.project.mvpframe.net.RetrofitManager
import com.project.mvpframe.ui.common.code_mvp.CodeModel
import com.project.mvpframe.util.encrypt.EncryptUtils
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * @CreateDate 2019/12/2 14:50
 * @Author jaylm
 */
class LoginModel : CodeModel() {
    fun login(
        username: String,
        password: String,
        verifyType: String,
        verifyCode: String
    ): Observable<ResponseBody> {
        val map = HashMap<String, Any>()
        map["username"] = username
        map["password"] = password
        if (!TextUtils.isEmpty(verifyType)) {
            map["verifyType"] = verifyType
        }
        if (!TextUtils.isEmpty(verifyCode)) {
            map["verifyCode"] = verifyCode
        }
        return RetrofitManager.getService(ApiService::class.java)
            .login(EncryptUtils.encrypt(map))
    }
}