package com.project.mvpframe.ui.activity

import android.view.View
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.ui.mvp.model.LoginModel
import com.project.mvpframe.ui.mvp.presenter.LoginPresenter
import com.project.mvpframe.ui.mvp.view.ILoginView

/**
 * @CreateDate 2019/12/2 15:32
 * @Author jaylm
 */
class LoginActivity : BaseActivity<LoginModel, LoginPresenter>(), ILoginView {
    override fun initMVP() {
        mPresenter.setMV(mModel, this)
    }

    override fun bindLayout(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(contentView: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}