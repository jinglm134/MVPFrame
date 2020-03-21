package com.project.mvpframe.ui.common.home_mvp

import android.os.Bundle
import android.os.Looper
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseFragment
import com.project.mvpframe.bean.App
import com.project.mvpframe.util.SnackBarUtils
import com.project.mvpframe.util.ToastUtils
import com.project.mvpframe.util.helper.bindArgument
import com.project.mvpframe.widget.Banner
import com.project.mvpframe.widget.ProvinceBottomSheet
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 首页fragment
 * @CreateDate 2019/12/19 11:57
 * @Author jaylm
 */
class HomeFragment : BaseFragment<HomePresenter>(),
    IHomeView {
    private val mIndex: Int by bindArgument("index")
    private var provinceBottomSheet: ProvinceBottomSheet? = null

    companion object {
        @JvmStatic
        fun newInstance(index: Int): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putInt("index", index)
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    override fun initMVP() {
        mPresenter.init(context!!, this)
    }

    override fun bindLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mPresenter.getBanner()

        Thread(Runnable {
            Looper.prepare()
            ToastUtils.showShortToast("sss")
            Looper.loop()
        }).start()

    }

    override fun setListener() {
        super.setListener()
        home_banner.setOnBannerItemClickListener(object : Banner.OnBannerItemClickListener {
            override fun onBannerItemClick(curIndex: Int) {
                ToastUtils.showShortToast(curIndex.toString())
            }
        })

        tv_btn1.setOnClickListener {
            if (provinceBottomSheet == null) {
                provinceBottomSheet = ProvinceBottomSheet(
                    mActivity,
                    object : ProvinceBottomSheet.OnSheetItemClickListener() {
                        override fun onSheetItemClick(data: String) {
                            SnackBarUtils.showSnackBar(mRootView, data)
                        }
                    })
            }
            provinceBottomSheet?.show()
        }
    }

    override fun getBannerSuccess(data: List<App>) {
        val urls = ArrayList<String>()
        data.forEach {
            urls.add(it.imageUrl)
        }
        home_banner.start(urls, ll_dot, R.mipmap.banner_dot_on, R.mipmap.banner_dot_off)
    }
}