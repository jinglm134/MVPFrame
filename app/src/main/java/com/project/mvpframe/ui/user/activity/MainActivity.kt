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
import com.project.mvpframe.ui.user.fragment.SecondFragment
import com.project.mvpframe.util.SnackBarUtils
import com.project.mvpframe.util.helper.bindExtra
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates


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
    private val mParam: Boolean by bindExtra("fromLogin")

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
            tabLayout.addTab(tab, false)
        }

        mFragments.add(SecondFragment.newInstance(0))
        mFragments.add(HomeFragment.newInstance(1))
        mFragments.add(HomeFragment.newInstance(2))
        mFragments.add(HomeFragment.newInstance(3))
        mFragments.add(HomeFragment.newInstance(4))

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
                    "${mFragments[tab.position]::class.java.name}${tab.position}"
                )
            }
        })
        tabLayout.getTabAt(0)?.select()
    }

    internal class ViewHolder(tabView: View) {
        var tvTab: TextView = tabView.findViewById(R.id.tv_tab)
        var ivTab: ImageView = tabView.findViewById(R.id.iv_tab)
    }

    private var backPressedTime by Delegates.observable(0L) { _, old, new ->
        //         2次的时间间隔小于1.5秒就退出了
        if (new - old <= 1500) {
            finish()
        } else {
            SnackBarUtils.showSnackBar(mContentView, "再次按返回键退出app", duration = 1500)
        }
    }

    // 从新写back方法
    override fun onBackPressed() {
        backPressedTime = System.currentTimeMillis()
    }
}