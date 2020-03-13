package com.project.mvpframe.ui.user.presenter

import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.bean.BannerBean
import com.project.mvpframe.net.BaseObserver
import com.project.mvpframe.net.RxHelper
import com.project.mvpframe.ui.user.model.HomeModel
import com.project.mvpframe.ui.user.view.IHomeView
import io.reactivex.observers.DisposableObserver
import java.util.*

/**
 * @CreateDate 2019/12/19 15:57
 * @Author jaylm
 */
class HomePresenter : BasePresenter<HomeModel, IHomeView>() {
    fun getBanner() {
        mModel.getBanner()
            .compose(RxHelper.observableIO2Main(mContext))
            .subscribe(object : DisposableObserver<BannerBean>() {
                override fun onComplete() {
                }
                override fun onNext(t: BannerBean) {
                    mView.getBannerSuccess(t.app)
                }
                override fun onError(e: Throwable) {
                }
            })
    }
}