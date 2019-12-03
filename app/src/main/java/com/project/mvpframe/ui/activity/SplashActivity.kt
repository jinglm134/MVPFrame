package com.project.mvpframe.ui.activity

import android.view.View
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.base.BaseModel
import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.base.IBaseView

/**
 * @CreateDate 2019/12/2 15:34
 * @Author jaylm
 */
class SplashActivity : BaseActivity<BaseModel, BasePresenter<BaseModel, IBaseView>>(), IBaseView {
    override fun initMVP() {
    }

    override fun initView(contentView: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindLayout(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}