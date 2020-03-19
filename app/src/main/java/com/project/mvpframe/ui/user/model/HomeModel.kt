package com.project.mvpframe.ui.user.model

import com.project.mvpframe.base.BaseModel
import com.project.mvpframe.bean.App
import com.project.mvpframe.bean.BannerBean
import com.project.mvpframe.net.ApiService
import com.project.mvpframe.net.RetrofitManager
import io.reactivex.Observable

/**
 * @CreateDate 2019/12/19 15:58
 * @Author jaylm
 */
class HomeModel : BaseModel() {
    fun getBanner(): Observable<List<App>?> {
        return RetrofitManager.getService(ApiService::class.java)
            .getBanner(System.currentTimeMillis())
            .map(object : Function1<BannerBean, List<App>> {
                override fun invoke(p1: BannerBean): List<App> {
                    return p1.app
                }
            })
    }
}