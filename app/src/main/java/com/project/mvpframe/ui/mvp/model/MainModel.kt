package com.project.mvpframe.ui.mvp.model

import com.google.gson.Gson
import com.project.mvpframe.base.BaseModel
import com.project.mvpframe.bean.BaseResponse
import com.project.mvpframe.bean.NoticeListBean
import com.project.mvpframe.bean.PrizeListBean
import com.project.mvpframe.net.ApiService
import com.project.mvpframe.net.RetrofitManager
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * @CreateDate 2019/11/29 10:21
 * @Author jaylm
 */
class MainModel : BaseModel() {
    fun getPrizeList(
        pageNumber: Int,
        pageSize: Int,
        status: String
    ): Observable<BaseResponse<List<PrizeListBean>>> {
        return RetrofitManager.getService(ApiService::class.java)
            .getPrizeList(pageNumber, pageSize, status)
    }

    fun getNoticeList(): Observable<BaseResponse<NoticeListBean>> {
        val params = HashMap<String, Any>()
        params["platform"] = 2
        params["typeId"] = 1
        params["pageSize"] = 20
        params["pageNumber"] = 1
        return RetrofitManager.getService(ApiService::class.java)
            .getNoticeList(
                RequestBody.create(
                    MediaType.parse("application/json"),
                    Gson().toJson(params)
                )
            )
    }
}