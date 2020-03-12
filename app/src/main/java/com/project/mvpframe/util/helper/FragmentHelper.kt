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

    /**
     * TabLayout+Fragment
     * Fragment替换(核心为隐藏当前的,显示现在的,用过的将不会destrory与create)
     */
    private var currentFragment: Fragment? = null

    fun smartReplaceFragment(
        fragmentManager: FragmentManager,
        @IdRes idRes: Int, toFragment: Fragment
    ) {
        smartReplaceFragment(fragmentManager, idRes, toFragment, toFragment.javaClass.simpleName)
    }

    fun smartReplaceFragment(
        fragmentManager: FragmentManager,
        @IdRes idRes: Int, toFragment: Fragment,
        tag: String
    ) {
        val transaction = fragmentManager.beginTransaction()
        // 如有当前在使用的->隐藏当前的
        if (currentFragment != null && !transaction.isEmpty) {
            transaction.hide(currentFragment!!)
        }
        // toFragment之前添加使用过->显示出来
        if (fragmentManager.findFragmentByTag(tag) != null) {
            transaction.show(toFragment)
        } else {// toFragment还没添加使用过->添加上去
            transaction.add(idRes, toFragment, tag)
        }
        transaction.commitAllowingStateLoss()
        // toFragment 更新为当前的
        currentFragment = toFragment
    }
}