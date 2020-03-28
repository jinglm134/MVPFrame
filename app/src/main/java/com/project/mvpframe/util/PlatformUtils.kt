package com.project.mvpframe.util

import android.content.Context
import android.content.pm.PackageManager

/**
 * @CreateDate 2020/3/28 16:37
 * @Author jaylm
 */
object PlatformUtils {
    const val PACKAGE_WX = "com.tencent.mm"
    const val ACTIVITY_WX = "com.tencent.mm.ui.tools.ShareImgUI"
    const val ACTIVITY_WX_CHAT = "com.tencent.mm.ui.tools.ShareToTimeLineUI"
    const val PACKAGE_MOBILE_QQ = "com.tencent.mobileqq"
    const val ACTIVITY_MOBILE_QQ = "com.tencent.mobileqq.activity.JumpActivity"
    //    const val PACKAGE_QZONE = "com.qzone"
    const val PACKAGE_SINA = "com.sina.weibo"

    // 判断是否安装指定app
    fun isInstallApp(context: Context, pkgName: String): Boolean {
        val packageManager = context.packageManager
        val pInfo = packageManager.getInstalledPackages(0)
        for (i in pInfo.indices) {
            val pn = pInfo[i].packageName
            if (pkgName == pn) {
                return true
            }
        }
        return false
    }

    fun checkAppInstalled(context: Context, pkgName: String): Boolean {
        if (pkgName.isEmpty()) {
            return false
        }
        return try {
            context.packageManager.getPackageInfo(pkgName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

    }

}