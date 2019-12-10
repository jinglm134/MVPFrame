package com.project.mvpframe.ui.mvp.presenter

import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.bean.LoginBean
import com.project.mvpframe.net.BaseObserver
import com.project.mvpframe.net.RxHelper
import com.project.mvpframe.ui.mvp.model.LoginModel
import com.project.mvpframe.ui.mvp.view.ILoginView

/**
 * @CreateDate 2019/12/2 14:51
 * @Author jaylm
 */
class LoginPresenter : BasePresenter<LoginModel, ILoginView>() {

    fun login(
        username: String,
        password: String,
        verifyType: String,
        verifyCode: String
    ) {
        mModel.login(username, password, verifyType, verifyCode)
            .compose(RxHelper.observableIO2Main(mContext))
            .subscribe(object : BaseObserver<LoginBean>(mContext) {
                override fun onSuccess(data: LoginBean) {
                    mView.successOfLogin(data)
                }
            })
    }
}