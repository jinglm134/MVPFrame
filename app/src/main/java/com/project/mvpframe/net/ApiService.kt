package com.project.mvpframe.net

import com.project.mvpframe.bean.BannerBean
import com.project.mvpframe.bean.BaseResponse
import com.project.mvpframe.bean.NoticeListBean
import com.project.mvpframe.bean.PrizeListBean
import com.project.mvpframe.constant.ApiConfig
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

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
        @Field("pageNumber") pageNumber: Int, @Field("pageSize") pageSize: Int, @Field(
            "status"
        ) status: String
    ): Observable<BaseResponse<List<PrizeListBean>>>

    @POST(ApiConfig.NOTICE_LIST)
    fun getNoticeList(@Body body: RequestBody): Observable<BaseResponse<NoticeListBean>>

    @POST(ApiConfig.LOGIN)
    fun login(@Body body: Map<String, String>): Observable<ResponseBody>

    @POST(ApiConfig.GET_CODE)
    fun getCode(@Body body: Map<String, String>): Observable<BaseResponse<String>>

    /*  @Headers("url_name:banner")
      @GET("{firstPathSegment}/${ApiConfig.GET_BANNER}")
      fun getBanner(@Path("firstPathSegment") firstPathSegment: String, @Query("v") time: Long): Observable<BaseResponse<BannerBean>>*/

    @Headers("url_name:banner")
    @GET(ApiConfig.GET_BANNER)
    fun getBanner(@Query("v") time: Long): Observable<BaseResponse<BannerBean>>
}