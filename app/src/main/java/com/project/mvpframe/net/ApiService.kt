package com.project.mvpframe.net

import com.project.mvpframe.bean.BaseResponse
import com.project.mvpframe.bean.LoginBean
import com.project.mvpframe.bean.NoticeListBean
import com.project.mvpframe.bean.PrizeListBean
import com.project.mvpframe.constant.ApiConfig
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
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
    @POST(ApiConfig.PRIZE_LIST)
    fun getPrizeList(
        @Field("pageNumber") pageNumber: Int,
        @Field("pageSize") pageSize: Int,
        @Field("status") status: String
    ): Observable<BaseResponse<List<PrizeListBean>>>

    @POST(ApiConfig.NOTICE_LIST)
    fun getNoticeList(
        @Body body: RequestBody
    ): Observable<BaseResponse<NoticeListBean>>

    @POST(ApiConfig.LOGIN)
    fun login(
        @Body body: RequestBody
    ): Observable<BaseResponse<LoginBean>>
}