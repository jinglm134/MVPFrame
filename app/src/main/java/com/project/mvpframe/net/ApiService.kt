package com.project.mvpframe.net

import com.project.mvpframe.bean.BaseBean
import com.project.mvpframe.bean.PrizeListBean
import com.project.mvpframe.constant.ApiConfig
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 请求接口类
 * @CreateDate 2019/12/2 11:03
 * @Author jaylm
 */
interface ApiService {
    //我的奖品列表
    @FormUrlEncoded
    @POST(ApiConfig.GET_PRIZE_LIST)
    fun getPrizeList(
        @Field("pageNumber") pageNumber: Int,
        @Field("pageSize") pageSize: Int,
        @Field("status") status: String
    ): Observable<BaseBean<List<PrizeListBean>>>
}