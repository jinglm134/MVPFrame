package com.project.mvpframe.ui.user.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseFragment
import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.ui.common.adapter.TestAdapter
import com.project.mvpframe.util.helper.FragmentHelper
import com.project.mvpframe.util.helper.bindArgument
import kotlinx.android.synthetic.main.fragment_second.*
import kotlin.properties.Delegates

/**
 * 首页fragment
 * @CreateDate 2019/12/19 11:57
 * @Author jaylm
 */
class SecondFragment : BaseFragment<BasePresenter<*, *>>() {
    val mIndex: Int by bindArgument("index")
    private val mFragments = ArrayList<Fragment>()
    private val mTitle = ArrayList<String>()
    private var mAdapter: TestAdapter by Delegates.notNull()
    private var mData = ArrayList<String>()

    companion object {
        @JvmStatic
        fun newInstance(index: Int): SecondFragment {
            val secondOneFragment = SecondFragment()
            val bundle = Bundle()
            bundle.putInt("index", index)
            secondOneFragment.arguments = bundle
            return secondOneFragment
        }
    }

    override fun initMVP() {
    }

    override fun bindLayout(): Int {
        return R.layout.fragment_second
    }

    override fun initView() {
        mFragments.add(SecondOneFragment.newInstance(0))
        mFragments.add(SecondOneFragment.newInstance(1))
        mFragments.add(SecondOneFragment.newInstance(2))
        mTitle.add("title1")
        mTitle.add("title2")
        mTitle.add("title3")

        FragmentHelper.initPagerAdapter(
            childFragmentManager,
            tabLayout,
            viewPager,
            mTitle,
            mFragments
        )

        recyclerView.isNestedScrollingEnabled = false
        recyclerView.setHasFixedSize(true)
        val manager = LinearLayoutManager(mActivity)
        manager.stackFromEnd = true
        manager.reverseLayout = true
        recyclerView.layoutManager = manager
        mAdapter = TestAdapter()
        recyclerView.adapter = mAdapter


        recyclerView2.isNestedScrollingEnabled = false
        recyclerView2.setHasFixedSize(true)
        recyclerView2.layoutManager = LinearLayoutManager(mActivity)
        recyclerView2.adapter = mAdapter
        setData()
    }

    private fun setData() {
        for (i in 0..30) {
            mData.add(i.toString())
        }
        mAdapter.setNewData(mData)
    }


}