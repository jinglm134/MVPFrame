package com.project.mvpframe.ui.common

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.widget.decoration.LinearDecoration
import kotlinx.android.synthetic.main.activity_choice_city.*

/**
 * @CreateDate 2020/3/20 10:27
 * @Author jaylm
 */
class ChoiceCityActivity : BaseActivity<BasePresenter<*, *>>() {


    override fun initMVP() {
    }

    override fun bindLayout(): Int {
        return R.layout.activity_choice_city
    }

    override fun initView(contentView: View) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(mActivity)
        recyclerView.addItemDecoration(LinearDecoration())
    }

    override fun setListener() {
        super.setListener()
    }
}