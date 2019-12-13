package com.project.mvpframe.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.bean.LoginBean
import com.project.mvpframe.constant.SPConst
import com.project.mvpframe.ui.mvp.model.LoginModel
import com.project.mvpframe.ui.mvp.presenter.LoginPresenter
import com.project.mvpframe.ui.mvp.view.ILoginView
import com.project.mvpframe.util.DialogUtils
import com.project.mvpframe.util.SPUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import java.util.*

/**
 * 启动页面
 * @CreateDate 2019/12/2 15:34
 * @Author jaylm
 */
class SplashActivity : BaseActivity<LoginModel, LoginPresenter>(), ILoginView {
    override fun successOfLogin(data: LoginBean) {
    }

    private var mTimer: Timer? = null

    override fun initMVP() {
        mPresenter.setMV(mModel, this)
    }

    override fun initView(contentView: View) {
    }

    override fun onStart() {
        super.onStart()
        requestPermission()
    }

    @SuppressLint("CheckResult")
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
                        mTimer!!.schedule(object : TimerTask() {
                            override fun run() {
                                if (SPUtils.getInstance(mActivity)
                                        .getParam(SPConst.SP_IS_LOGIN, false)
                                ) {
                                    startActivity(MainActivity::class.java)
                                } else {
                                    startActivity(LoginActivity::class.java)
                                }
                                finish()

                            }
                        }, 1000)
                    }
                    permission.shouldShowRequestPermissionRationale -> {
                        DialogUtils.showTwoDialog(mActivity,
                            "请允许开启权限以正常使用app功能",
                            "确定",
                            View.OnClickListener {
                                requestPermission()
                            },
                            View.OnClickListener { finish() })
                    }
                    else -> {
                        DialogUtils.showTwoDialog(mActivity,
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
            }
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