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
     * TabLayout+ViewPager+Fragment,设置tabLayout并返回adapter
     *
     * @param fragmentManager       FragmentManager getSupportFragmentManager或者getChildFragmentManager
     * @param tabLayout             tabLayout
     * @param viewPager             viewPager
     * @param mTitleList            标题文字
     * @param mFragmentList         fragments
     * @param num                   设置预加载数
     * @param tabMode                设置滑动模式
     * @return                      adapter
     */
    fun initPagerAdapter(
        fragmentManager: FragmentManager,
        tabLayout: TabLayout,
        viewPager: ViewPager,
        mTitleList: List<String>,
        mFragmentList: List<Fragment>,
        num: Int = 1,
        tabMode: Int = TabLayout.MODE_FIXED
    ) {
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = tabMode
        viewPager.adapter = object : FragmentPagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragmentList[position]
            }

            override fun getCount(): Int {
                return mTitleList.size.coerceAtMost(mFragmentList.size)//页面数量
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return mTitleList[position % count]
            }
        }
        viewPager.offscreenPageLimit = num //设置预加载个数
        viewPager.currentItem = 0//设置当前加载页面
    }

    /**
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