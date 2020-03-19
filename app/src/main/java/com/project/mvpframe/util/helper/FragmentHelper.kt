package com.project.mvpframe.util.helper

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

/**
 * @CreateDate 2020/3/12 14:03
 * @Author jaylm
 */
object FragmentHelper {

    /**
     * TabLayout+ViewPager+Fragment
     *
     * @param fragmentManager       FragmentManager getSupportFragmentManager或者getChildFragmentManager
     * @param tabLayout             tabLayout
     * @param viewPager             viewPager
     * @param titles                标题文字
     * @param fragments             fragments
     * @param pageLimitNum          预加载数
     * @param tabMode               滑动模式
     */
    fun initPagerAdapter(
        fragmentManager: FragmentManager,
        tabLayout: TabLayout,
        viewPager: ViewPager,
        titles: List<String>,
        fragments: List<Fragment>,
        pageLimitNum: Int = 1,
        tabMode: Int = TabLayout.MODE_FIXED
    ) {
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = tabMode
        viewPager.adapter = object : FragmentPagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return titles.size.coerceAtMost(fragments.size)//页面数量
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles[position % count]
            }
        }
        viewPager.offscreenPageLimit = pageLimitNum //设置预加载个数
        viewPager.currentItem = 0//设置当前加载页面
    }
}