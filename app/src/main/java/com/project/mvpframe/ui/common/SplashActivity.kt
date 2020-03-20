package com.project.mvpframe.ui.common

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.constant.SPConst
import com.project.mvpframe.ui.common.login_mvp.LoginActivity
import com.project.mvpframe.ui.common.main_mvp.MainActivity
import com.project.mvpframe.util.DialogUtils
import com.project.mvpframe.util.SPUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * 启动页面
 * @CreateDate 2019/12/2 15:34
 * @Author jaylm
 */
class SplashActivity : BaseActivity<BasePresenter<*, *>>() {

    private var mTimer: Timer? = null

    override fun initMVP() {
//        mPresenter.init(this, this)
    }

    override fun initView(contentView: View) {
    }

    override fun onStart() {
        super.onStart()
        requestPermission()
    }

    private fun requestPermission() {
        RxPermissions(this)
            .requestEachCombined(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .subscribe { permission ->
                when {
                    permission.granted -> {
                        if (mTimer == null) {
                            mTimer = Timer()
                        }
                        mTimer?.schedule(object : TimerTask() {
                            override fun run() {
                                if (SPUtils.getInstance().getParam(SPConst.SP_IS_LOGIN, false)) {
                                    startActivity(MainActivity::class.java)
                                } else {
                                    startActivity(LoginActivity::class.java)
                                }
                                finish()
                            }
                        }, 1000)
                    }

                    permission.shouldShowRequestPermissionRationale -> {
                        DialogUtils.showTwoDialog(
                            mActivity,
                            "请允许开启权限以正常使用app功能",
                            "确定",
                            View.OnClickListener {
                                requestPermission()
                            },
                            View.OnClickListener { finish() })
                    }

                    else -> {
                        DialogUtils.showTwoDialog(
                            mActivity,
                            "请在设置中开启权限,以正常使用app功能",
                            "去设置",
                            View.OnClickListener {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intent.data = Uri.parse("package:$packageName")
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            },
                            View.OnClickListener { finish() })
                    }
                }
            }.addTo(CompositeDisposable())
    }

    private fun Disposable.addTo(c: CompositeDisposable) {
        c.add(this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_splash
    }

    override fun onStop() {
        super.onStop()
        if (mTimer != null) {
            mTimer!!.cancel()
            mTimer = null
        }
    }

}