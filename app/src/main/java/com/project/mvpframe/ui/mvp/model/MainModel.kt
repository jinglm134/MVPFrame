package com.project.mvpframe.ui.mvp.model

import android.content.Context
import com.project.mvpframe.base.BaseModel
import com.project.mvpframe.bean.PrizeListBean
import com.project.mvpframe.net.ApiService
import com.project.mvpframe.net.BaseObserver
import com.project.mvpframe.net.RetrofitManager
import com.project.mvpframe.net.RxHelper

/**
 * @CreateDate 2019/11/29 10:21
 * @Author jaylm
 */
class MainModel : BaseModel() {
    fun getPrizeList(
        mContext: Context,
        pageNumber: Int,
        pageSize: Int,
        status: String,
        observer: BaseObserver<List<PrizeListBean>>
    ) {
        RetrofitManager.getService(ApiService::class.java)
            .getPrizeList(pageNumber, pageSize, status)
            .compose(RxHelper.observableIO2Main(mContext))
            .subscribe(observer)
    }
}