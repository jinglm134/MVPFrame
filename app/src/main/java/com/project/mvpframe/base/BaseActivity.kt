package com.project.mvpframe.base

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.project.mvpframe.BuildConfig
import com.project.mvpframe.R
import com.project.mvpframe.ui.activity.MainActivity
import com.project.mvpframe.util.helper.ClassReflectHelper
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import java.util.*

/**
 * @CreateDate 2019/11/28 18:18
 * @Author jaylm
 */
abstract class BaseActivity<M : BaseModel, P : BasePresenter<*, *>> :
    RxAppCompatActivity(), IBaseView {

    /**Tag为类名,用于日志输出的Tag或它用 */
    protected val TAG by lazy { this.javaClass.simpleName }
    /** 是否输出日志信息 */
    protected val mDebug by lazy { BuildConfig.DEBUG }
    /** 是否沉浸状态栏 */
    open var isSetStatusBar = false
    /** 是否允许全屏 */
    open var mAllowFullScreen = false
    /** 允许旋转屏幕还是保持竖屏 */
    open var isAllowScreenRotate = false
    /** 当前Activity渲染的视图View */
    lateinit var mContentView: View
    /** 用来保存所有在栈内的Activity  */
    private val mActivityStacks = Stack<Activity>()
    protected lateinit var mContext: Activity
    protected lateinit var mPresenter: P
    protected lateinit var mModel: M
    protected var mUnbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //关闭权限后,app进程被杀死,进入app恢复当前页面时重启app
        if (savedInstanceState != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }


        //MVP
        mModel = ClassReflectHelper.getT(this, 0)
        mPresenter = ClassReflectHelper.getT(this, 1)
        //context
        mContext = this

        if (mAllowFullScreen) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        if (isSetStatusBar) {
            steepStatusBar()
        }

        requestedOrientation = if (isAllowScreenRotate) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        setContentView(R.layout.activity_base)
        initToolbar()
        val contentView = LayoutInflater.from(mContext).inflate(bindLayout(), base_container, true)
        // 将activity推入栈中
        mActivityStacks.push(this)
        mUnbinder = ButterKnife.bind(this)

        initView(contentView)
        setListener()
    }

    @LayoutRes
    abstract fun bindLayout(): Int

    abstract fun initView(contentView: View)
    protected open fun setListener() {}

    /**
     * 沉浸状态栏
     */
    private fun steepStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        // 透明状态栏
        window.addFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        // 透明导航栏
        window.addFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
//        }
    }

    private fun initToolbar() {
        setSupportActionBar(base_toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)//显示返回图标
            actionBar.setDisplayShowTitleEnabled(false)//不显示应用图标
        }
    }

    //设置toolBar标题
    protected fun setHeader(text: CharSequence) {
        base_toolbar.visibility = View.VISIBLE
        base_title.text = text
    }

    //设置toolbar右侧按钮
    protected fun setRightButton(
        text: CharSequence, @Nullable @DrawableRes drawableRes: Int,
        @Nullable clickListener: View.OnClickListener
    ) {
        base_right_text.text = text
        base_right_text.setCompoundDrawablesWithIntrinsicBounds(drawableRes, 0, 0, 0)
        base_right_text.setOnClickListener(clickListener)
    }

    /**
     * 页面跳转
     *
     * @param clz class
     */
    fun startActivity(clz: Class<*>) {
        startActivity(clz, null)
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz class
     * @param bundle bundle
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent()
        intent.setClass(mContext, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }


    /**
     * 日志输出
     * @param msg
     */
    protected fun log(msg: String) {
        if (mDebug) {
            Log.d(TAG, msg)
        }
    }

    /**
     * Fragment替换(核心为隐藏当前的,显示现在的,用过的将不会destrory与create)
     */
    private var currentFragment: Fragment? = null

    protected fun smartReplaceFragment(@IdRes idRes: Int, toFragment: Fragment, tag: String) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        // 如有当前在使用的->隐藏当前的
        if (currentFragment != null) {
            transaction.hide(currentFragment!!)
        }
        // toFragment之前添加使用过->显示出来
        if (manager.findFragmentByTag(tag) != null) {
            transaction.show(toFragment)
        } else {// toFragment还没添加使用过->添加上去
            transaction.add(idRes, toFragment, tag)
        }
        transaction.commitAllowingStateLoss()
        // toFragment 更新为当前的
        currentFragment = toFragment
    }

    fun smartReplaceFragment(@IdRes idRes: Int, toFragment: Fragment) {
        smartReplaceFragment(idRes, toFragment, toFragment.javaClass.simpleName)
    }


    //IBaseView
    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mUnbinder != null && mUnbinder !== Unbinder.EMPTY) {
            mUnbinder!!.unbind()
        }
        if (mActivityStacks.contains(this)) {
            mActivityStacks.remove(this)
        }
    }
}