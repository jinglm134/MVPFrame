package com.project.mvpframe.ui.common.home_mvp

import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.bean.App
import com.project.mvpframe.net.RxHelper
import io.reactivex.observers.DisposableObserver

/**
 * @CreateDate 2019/12/19 15:57
 * @Author jaylm
 */
class HomePresenter : BasePresenter<HomeModel, IHomeView>() {
    fun getBanner() {
        mModel.getBanner()
            .compose(RxHelper.observableIO2Main(mContext))
            .subscribe(object : DisposableObserver<List<App>>() {
                override fun onComplete() {
                }

                override fun onNext(t: List<App>) {
                    mView.getBannerSuccess(t)
                }

                override fun onError(e: Throwable) {
                }
            })
    }
}