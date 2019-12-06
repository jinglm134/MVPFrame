package com.project.mvpframe.ui.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.app.NotificationCompat
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.ui.mvp.model.LoginModel
import com.project.mvpframe.ui.mvp.presenter.LoginPresenter
import com.project.mvpframe.ui.mvp.view.ILoginView
import java.util.*

/**
 * @CreateDate 2019/12/2 15:32
 * @Author jaylm
 */
class LoginActivity : BaseActivity<LoginModel, LoginPresenter>(), ILoginView {
    override fun initMVP() {
        mPresenter.setMV(mModel, this)
    }

    override fun bindLayout(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(contentView: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


//    val NOTIFY_DOWN_ID = 2000
//     var mNotificationManager: NotificationManager
//     var mNotificationCompat: NotificationCompat.Builder
//
//    private fun initNotification() {
//        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //            // 创建
//            val channel = NotificationChannel(
//                NOTIFY_DOWN_ID.toString(),
//                "通知",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            channel.enableLights(true)
//            channel.setShowBadge(true)
//            channel.description = "app更新"
//            mNotificationManager.createNotificationChannel(channel)
//        }
//        val mIntent = Intent(this, SplashActivity::class.java)
//        mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//        val mPendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0)
//        mNotificationCompat = NotificationCompat.Builder(this)
//        mNotificationCompat.setContentText("已下载:0%")
//            //        .setContentTitle("MyAPP");
//            .setSmallIcon(R.mipmap.bibr_launcher)
//            .setDefaults(NotificationCompat.DEFAULT_ALL).priority =
//            NotificationCompat.PRIORITY_DEFAULT
//        mNotificationManager.notify(1000, mNotificationCompat.build())
//    }
//
//    private var currentProgress = -1
//
//    private fun notificationUpdate(progress: Int) {
//        if (progress == currentProgress) {
//            return
//        }
//        currentProgress = progress
//
//        val contentView = RemoteViews(packageName, R.layout.notification_download)
//        contentView.setProgressBar(R.id.pb_notification, 100, progress, false)
//        contentView.setTextViewText(
//            R.id.textView_notification,
//            String.format(Locale.CHINA, "下载进度: %d%%", progress)
//        )
//
//        //设置为正在进行时的Notification(用户无法取消这条通知)
//        mNotificationCompat.setOngoing(progress != 100)
//            .setCustomContentView(contentView)
//        //显示这条通知，id为0
//        mNotificationManager.notify(1000, mNotificationCompat.build())
//    }
}