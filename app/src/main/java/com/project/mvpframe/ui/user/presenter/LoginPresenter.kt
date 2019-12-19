package com.project.mvpframe.ui.user.presenter

import com.project.mvpframe.bean.LoginBean
import com.project.mvpframe.net.BaseObserverString
import com.project.mvpframe.net.RxHelper
import com.project.mvpframe.ui.common.presenter.CodePresenter
import com.project.mvpframe.ui.user.model.LoginModel
import com.project.mvpframe.ui.user.view.ILoginView
import com.project.mvpframe.util.GsonUtils

/**
 * @CreateDate 2019/12/2 14:51
 * @Author jaylm
 */
class LoginPresenter : CodePresenter<LoginModel, ILoginView>() {

    fun login(
        username: String,
        password: String,
        verifyType: String,
        verifyCode: String
    ) {
        mModel.login(username, password, verifyType, verifyCode)
            .compose(RxHelper.observableIO2Main(mContext))
            .subscribe(object : BaseObserverString(mContext) {
                override fun onSuccess(data: String) {
                    mView.showToast("登陆成功")
                    mView.loginSuccess(GsonUtils.parseJsonWithGson(data, LoginBean::class.java))
                }

                override fun onFailure(code: Int, msg: String) {
                    super.onFailure(code, msg)
                    mView.loginFailWithCode(code)
                }
            })
    }

}