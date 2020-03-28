package com.project.mvpframe.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver

/**
 * 申请权限
 * @CreateDate 2020/3/28 14:23
 * @Author jaylm
 */
object PermissionUtils {

    fun requestPermission(fragment: Fragment,
                          permissions: Array<String>,
                          callBack: OnPermissionCallBack) {
        requestPermission(fragment.activity, permissions, callBack)
    }

    fun requestPermission(mContext: Context?,
                          permissions: Array<String>,
                          callBack: OnPermissionCallBack) {
        if (mContext == null) {
            ToastUtils.showShortToast("Context instantiate error, can not get storage permission")
            return
        }

        val rxPermission: RxPermissions = when (mContext) {
            is FragmentActivity -> RxPermissions(mContext)
            is Fragment -> RxPermissions(mContext)
            else -> {
                ToastUtils.showShortToast("Context instantiate error, can not get storage permission")
                return
            }
        }

        rxPermission.requestEachCombined(*permissions).subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<Permission>() {
                override fun onNext(t: Permission) {
                    when {
                        t.granted -> {
                            callBack.permissionSucceed()
                        }
                        t.shouldShowRequestPermissionRationale -> DialogUtils.showTwoDialog(mContext,
                            "请允许开启权限以正常使用app功能",
                            "确定",
                            View.OnClickListener {
                                requestPermission(mContext, permissions, callBack)
                            },
                            View.OnClickListener { })
                        else -> DialogUtils.showTwoDialog(mContext,
                            "请在设置中开启权限,以正常使用app功能",
                            "去设置",
                            View.OnClickListener {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intent.data = Uri.parse("package:${mContext.packageName}")
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                mContext.startActivity(intent)
                            },
                            View.OnClickListener { })
                    }
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {
                }

            })
    }

    interface OnPermissionCallBack {
        fun permissionSucceed()
    }
}