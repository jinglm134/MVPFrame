package com.project.mvpframe.base

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import butterknife.ButterKnife
import butterknife.Unbinder
import com.project.mvpframe.BuildConfig
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
    private var mBind: Unbinder? = null
    private var rootView: View? = null
    lateinit var mActivity: BaseActivity<*>

    protected var hasConfig = false//是否已调用过initView/setListeners

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity as BaseActivity<*>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //MVP
        mPresenter = ClassReflectHelper.getT(this, 0)
//        mPresenter.init(context!!)
        initMVP()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(bindLayout(), container, false)
            mBind = ButterKnife.bind(this, rootView!!)
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
        if (!hasConfig) {
            initView()
            setListener()
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
        if (mDebug) {
            Log.v(TAG, msg)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        rootView = null
        if (mBind != null && mBind !== Unbinder.EMPTY) {
            mBind!!.unbind()
        }
    }
}