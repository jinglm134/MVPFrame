package com.project.mvpframe.ui.user.view

import com.project.mvpframe.base.IBaseView
import com.project.mvpframe.bean.LoginBean

/**
 * @CreateDate 2019/12/2 14:52
 * @Author jaylm
 */
interface ILoginView : IBaseView {
    fun successOfLogin(data: LoginBean)
    fun codeOfLogin(code: Int)
}