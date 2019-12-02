package com.project.mvpframe.ui.activity

import android.Manifest
import android.view.View
import android.widget.Toast
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.net.RetrofitManager
import com.project.mvpframe.ui.mvp.model.MainModel
import com.project.mvpframe.ui.mvp.presenter.MainPresenter
import com.project.mvpframe.ui.mvp.view.IMainView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainModel, MainPresenter>(),
    IMainView {
    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView(contentView: View) {
        mPresenter.setMV(mModel, this)
    }

    override fun setListener() {
        super.setListener()
        tv_hello.setOnClickListener {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show()
            val rxPermissions = RxPermissions(this)
            rxPermissions
                .requestEach(
                    Manifest.permission.CAMERA
                )
                .subscribe { permission ->
                    when {
                        permission.granted -> {
                            // `permission.name` is granted !
                        }
                        permission.shouldShowRequestPermissionRationale -> {
                            // Denied permission without ask never again
                        }
                        else -> {
                            // Denied permission with ask never again
                            // Need to go to the settings
//                            AppSettingsDialog.Builder(this@BasePermissionActivity)
//                                .setTitle("设置权限")
//                                .setRationale("需要开启权限才能使用此功能")
//                                .setPositiveButton("设置")
//                                .build()
//                                .show()
                        }
                    }
                }
        }
    }
}