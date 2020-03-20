package com.project.mvpframe.ui.common.login_mvp

import com.project.mvpframe.bean.LoginBean
import com.project.mvpframe.ui.common.code_mvp.ICodeView

/**
 * @CreateDate 2019/12/2 14:52
 * @Author jaylm
 */
interface ILoginView : ICodeView {
    fun loginSuccess(data: LoginBean)
    fun loginFailWithCode(code: Int)
}