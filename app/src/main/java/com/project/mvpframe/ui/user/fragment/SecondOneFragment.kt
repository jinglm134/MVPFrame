package com.project.mvpframe.ui.user.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseFragment
import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.ui.user.adapter.TestAdapter
import com.project.mvpframe.util.helper.bindArgument
import kotlinx.android.synthetic.main.layout_smart_refresh.*
import kotlin.properties.Delegates

/**
 * @CreateDate 2020/3/16 18:27
 * @Author jaylm
 */
class SecondOneFragment : BaseFragment<BasePresenter<*, *>>() {
    val mIndex: Int by bindArgument("index")

    companion object {
        fun newInstance(index: Int): SecondOneFragment {
            val secondOneFragment = SecondOneFragment()
            val bundle = Bundle()
            bundle.putInt("index", index)
            secondOneFragment.arguments = bundle
            return secondOneFragment
        }
    }

    private var mAdapter: TestAdapter by Delegates.notNull()
    private var mData = ArrayList<String>()

    override fun initMVP() {
    }

    override fun bindLayout(): Int {
        return R.layout.layout_smart_refresh
    }

    override fun initView() {
        smartRefreshLayout.setEnableRefresh(false)
        smartRefreshLayout.setEnableLoadMore(false)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(mActivity)
        mAdapter = TestAdapter()
        recyclerView.adapter = mAdapter

        setData()
    }

    private fun setData() {
        for (i in 0..mIndex * 10) {
            mData.add(i.toString())
        }
        mAdapter.setNewData(mData)
    }
}