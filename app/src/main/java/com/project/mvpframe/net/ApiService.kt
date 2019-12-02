package com.project.mvpframe.net

import com.project.mvpframe.base.BaseBean
import com.project.mvpframe.bean.PrizeListBean
import com.project.mvpframe.constant.ApiConfig
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

/**
 * @CreateDate 2019/12/2 11:03
 * @Author jaylm
 */
interface ApiService {
    //我的奖品列表
    @POST(ApiConfig.GET_PRIZE_LIST)
    fun getPrizeList(@HeaderMap map: Map<String, String>, @Body bodyMap: Map<String, String>): Observable<BaseBean<List<PrizeListBean>>>
}