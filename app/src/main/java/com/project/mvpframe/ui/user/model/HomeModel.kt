package com.project.mvpframe.ui.user.model

import com.project.mvpframe.BuildConfig
import com.project.mvpframe.base.BaseModel
import com.project.mvpframe.bean.BannerBean
import com.project.mvpframe.bean.BaseResponse
import com.project.mvpframe.net.ApiService
import com.project.mvpframe.net.RetrofitManager
import io.reactivex.Observable

/**
 * @CreateDate 2019/12/19 15:58
 * @Author jaylm
 */
class HomeModel : BaseModel() {
    fun getBanner(): Observable<BaseResponse<BannerBean>> {
        return RetrofitManager.getService(ApiService::class.java)
            .getBanner(System.currentTimeMillis())
    }
}