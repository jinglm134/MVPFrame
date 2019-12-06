package com.project.mvpframe.ui.mvp.presenter

import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.net.ApiService
import com.project.mvpframe.net.RetrofitManager
import com.project.mvpframe.ui.mvp.model.LoginModel
import com.project.mvpframe.ui.mvp.view.ILoginView

/**
 * @CreateDate 2019/12/2 14:51
 * @Author jaylm
 */
class LoginPresenter : BasePresenter<LoginModel, ILoginView>() {

    fun login() {
    }
}