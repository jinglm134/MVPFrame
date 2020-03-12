package com.project.mvpframe.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import com.project.mvpframe.BuildConfig
import com.project.mvpframe.util.LogUtils
import com.project.mvpframe.util.ToastUtils
import com.project.mvpframe.util.helper.ClassReflectHelper
import com.trello.rxlifecycle2.components.support.RxFragment


/**
 * @CreateDate 2019/12/2 17:35
 * @Author jaylm
 */
abstract class BaseFragment<P : BasePresenter<*, *>> : RxFragment()
    , IBaseView {

    companion object {
        /**Tag为类名,用于日志输出的Tag或它用 */
        protected val TAG by lazy { this::class.java.simpleName }
        /** 是否输出日志信息 */
        protected val mDebug by lazy { BuildConfig.DEBUG }
    }

    protected lateinit var mPresenter: P
    private var rootView: View? = null
    lateinit var mActivity: BaseActivity<*>

    private var hasConfig =
        false//是否已调用过initView/setListeners,因为fragment恢复时,onCreateView等生命周期方法会重新调用
    /*默认可见,用于懒加载*/
    private var hasVisible = true

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as BaseActivity<*>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //MVP
        try {
            //防止不使用mvp时出现异常
            mPresenter = ClassReflectHelper.getT(this, 0)
            initMVP()
        } catch (e: ClassCastException) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(bindLayout(), container, false)
        }

        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        val parent: ViewParent? = rootView!!.parent
        if (parent != null) {
            (parent as ViewGroup).removeView(rootView)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onVisible()
    }

    /*使用viewpager的FragmentPagerAdapter时，会调用setUserVisibleHint,并且在onCreateView之前调用,实现懒加载*/
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            hasVisible = true
            onVisible()
        } else {
            hasVisible = false
        }
    }

    private fun onVisible() {
        if (hasVisible && rootView != null && !hasConfig) {
            /*onViewCreated 和 setUserVisibleHint 均可能调用该方法,以下代码只被执行一次.
              onViewCreated:未使用FragmentPagerAdapter加载的fragment ，或者FragmentPagerAdapter中的第一个fragment,因为hasVisible=true会执行以下代码
              setUserVisibleHint：使用FragmentPagerAdapter加载的fragment,且非FragmentPagerAdapter中的第一个fragment。执行onViewCreated,由于它们的hasVisible=false,所以不会执行以下代码,当fragment可见时才会执行以下代码*/
            initView()//初始化view
            setListener()//设置监听
            hasConfig = true
        }
    }

    protected abstract fun initMVP()
    protected abstract fun bindLayout(): Int
    protected abstract fun initView()
    open fun setListener() {}


    /**
     * 日志输出
     * @param msg
     */
    protected fun log(msg: String) {
        LogUtils.v(TAG, msg)
    }

    override fun showToast(str: CharSequence) {
        ToastUtils.showShortToast(str)
    }

    override fun onDestroy() {
        super.onDestroy()
        rootView = null
    }
}