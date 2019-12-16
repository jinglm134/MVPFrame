package com.project.mvpframe.ui.user.activity

import android.view.View
import android.widget.Toast
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.bean.Notice
import com.project.mvpframe.bean.PrizeListBean
import com.project.mvpframe.ui.user.presenter.MainPresenter
import com.project.mvpframe.ui.user.view.IMainView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.ParameterizedType


/**
 * 首页面
 * @CreateDate 2019/12/3 08:34
 * @Author jaylm
 */
class MainActivity : BaseActivity<MainPresenter>(),
    IMainView {


    companion object {
        const val PAGE_SIZE = 10
    }

    override fun initMVP() {
        mPresenter.init(this, this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView(contentView: View) {
        mPresenter.getNoticeList()
    }

    override fun setListener() {
        super.setListener()
        tv_hello.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show()
        }
    }

    override fun successOfNotice(data: List<Notice>) {

    }

    override fun successOfPrize(data: List<PrizeListBean>) {
    }
}