package com.project.mvpframe.ui.mvp.model

import com.google.gson.Gson
import com.project.mvpframe.base.BaseModel
import com.project.mvpframe.bean.BaseResponse
import com.project.mvpframe.bean.LoginBean
import com.project.mvpframe.net.ApiService
import com.project.mvpframe.net.RetrofitManager
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody

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
        map["verifyType"] = verifyType
        map["verifyCode"] = verifyCode
        return RetrofitManager.getService(ApiService::class.java)
            .login(RequestBody.create(MediaType.parse("application/json"), Gson().toJson(map)))
    }
}