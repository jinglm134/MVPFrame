package com.project.mvpframe.ui.user.activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.ui.user.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 首页面
 * @CreateDate 2019/12/3 08:34
 * @Author jaylm
 */
class MainActivity : BaseActivity<BasePresenter<*, *>>() {

    //tabLayout标题
    private val mTitles = arrayOf("首页", "行情", "交易", "法币", "资产")
    //tabLayout的icon
    private val mIconRes = intArrayOf(
        R.drawable.selector_icon_home,
        R.drawable.selector_icon_quotes,
        R.drawable.selector_icon_deal,
        R.drawable.selector_icon_fabi,
        R.drawable.selector_icon_wallet
    )
    private val mFragments = ArrayList<Fragment>(5)


    override fun initMVP() {
    }

    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView(contentView: View) {
        for (i in mTitles.indices) {
            val tab = tabLayout.newTab().setCustomView(R.layout.tablayout_main)
            val holder = ViewHolder(tab.customView!!)

            holder.ivTab.setImageResource(mIconRes[i])
            holder.tvTab.text = mTitles[i]
            tabLayout.addTab(tab)
        }

        mFragments.add(HomeFragment.newInstance())
        mFragments.add(HomeFragment.newInstance())
        mFragments.add(HomeFragment.newInstance())
        mFragments.add(HomeFragment.newInstance())
        mFragments.add(HomeFragment.newInstance())
    }

    override fun setListener() {
        super.setListener()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                smartReplaceFragment(
                    R.id.main_container, mFragments[tab.position],
                    "${mFragments.javaClass.simpleName}+${tab.position}"
                )
            }
        })

        tabLayout.getTabAt(0)!!.select()
    }

    internal class ViewHolder(tabView: View) {
        var tvTab: TextView = tabView.findViewById(R.id.tv_tab)
        var ivTab: ImageView = tabView.findViewById(R.id.iv_tab)
    }
}