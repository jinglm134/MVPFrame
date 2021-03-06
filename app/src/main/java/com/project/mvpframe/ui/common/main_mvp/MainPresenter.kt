package com.project.mvpframe.ui.common.main_mvp

import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.bean.NoticeListBean
import com.project.mvpframe.bean.PrizeListBean
import com.project.mvpframe.net.BaseObserver
import com.project.mvpframe.net.RxHelper

/**
 * @CreateDate 2019/11/29 10:23
 * @Author jaylm
 */
class MainPresenter : BasePresenter<MainModel, IMainView>() {

    fun getPrizeList(pageNumber: Int, pageSize: Int, status: String) {
        mModel.getPrizeList(
            pageNumber,
            pageSize,
            status
        ).compose(RxHelper.observableIO2Main(mContext))
            .subscribe(object : BaseObserver<List<PrizeListBean>>(mContext) {
                override fun onSuccess(data: List<PrizeListBean>) {
                    mView.prizeSuccess(data)
                }
            })
    }

    fun getNoticeList() {
        mModel.getNoticeList().compose(RxHelper.observableIO2Main(mContext))
            .subscribe(object : BaseObserver<NoticeListBean>(mContext) {
                override fun onSuccess(data: NoticeListBean) {
                    mView.noticeSuccess(data.list)
                }
            })
    }
}