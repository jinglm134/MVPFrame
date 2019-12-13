package com.project.mvpframe.ui.mvp.model

import com.project.mvpframe.base.BaseModel
import com.project.mvpframe.bean.BaseResponse
import com.project.mvpframe.bean.LoginBean
import com.project.mvpframe.net.ApiService
import com.project.mvpframe.net.RetrofitManager
import com.project.mvpframe.util.encrypt.EncryptUtils
import io.reactivex.Observable

/**
 * @CreateDate 2019/12/2 14:50
 * @Author jaylm
 */
class LoginModel : BaseModel() {
    fun login(
        username: String,
        password: String,
        verifyType: String,
        verifyCode: String
    ): Observable<BaseResponse<LoginBean>> {
        val map = HashMap<String, Any>()
        map["username"] = username
        map["password"] = password
        /*map["verifyType"] = verifyType
        map["verifyCode"] = verifyCode*/
        return RetrofitManager.getService(ApiService::class.java)
            .login(EncryptUtils.encrypt(map))
    }
}