package com.project.mvpframe.ui.common

import android.view.View
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.base.BasePresenter
import kotlinx.android.synthetic.main.layout_smart_refresh.*

/**
 * @CreateDate 2020/3/20 10:27
 * @Author jaylm
 */
class ChoiceCityActivity : BaseActivity<BasePresenter<*, *>>() {


    override fun initMVP() {
    }

    override fun bindLayout(): Int {
        return R.layout.layout_smart_refresh
    }

    override fun initView(contentView: View) {
        smartRefreshLayout.setEnableLoadMore(false)
        smartRefreshLayout.setEnableRefresh(false)
    }
}