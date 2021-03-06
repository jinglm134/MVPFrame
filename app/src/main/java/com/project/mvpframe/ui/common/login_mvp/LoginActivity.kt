package com.project.mvpframe.ui.common.login_mvp

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.bean.LoginBean
import com.project.mvpframe.constant.ApiConfig
import com.project.mvpframe.constant.ApiDomain
import com.project.mvpframe.constant.SPConst
import com.project.mvpframe.ui.common.main_mvp.MainActivity
import com.project.mvpframe.util.RegexUtils
import com.project.mvpframe.util.SPUtils
import com.project.mvpframe.util.UShape
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录页面
 * @CreateDate 2019/12/2 15:32
 * @Author jaylm
 */
class LoginActivity : BaseActivity<LoginPresenter>(), ILoginView {

    private var mTimer: CountDownTimer? = null
    private var mCode = 0

    //    private var data by PrefDelegate(SPConst.SP_LOGIN_DATA, "")
    //    private var token by PrefDelegate(SPConst.SP_TOKEN, "")
    //    private var userid by PrefDelegate(SPConst.SP_USER_ID, "")
    //    private var isLogin by PrefDelegate(SPConst.SP_IS_LOGIN, false)

    override fun initMVP() {
        mPresenter.init(this, this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView(contentView: View) {
        //初始化view背景
        UShape.setBackgroundDrawable(UShape.getPressedDrawable(UShape.getColor(R.color.colorPrimary),
            UShape.getColor(R.color.blue),
            4), btn_login)
        UShape.setBackgroundDrawable(UShape.getPressedDrawable(UShape.getColor(R.color.colorPrimary),
            UShape.getColor(R.color.blue),
            4), btn_code)
        UShape.setBackgroundDrawable(UShape.getStrokeDrawable(UShape.getColor(R.color.black_c),
            UShape.getColor(R.color.white),
            4), et_account)
        UShape.setBackgroundDrawable(UShape.getStrokeDrawable(UShape.getColor(R.color.black_c),
            UShape.getColor(R.color.white),
            4), ll_password)
        UShape.setBackgroundDrawable(UShape.getStrokeDrawable(UShape.getColor(R.color.black_c),
            UShape.getColor(R.color.white),
            4), et_code)

    }

    override fun setListener() {
        super.setListener()

        //账户输入框
        et_account.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val account = s.toString().trim()
                if (account.length != 11) {
                    tv_account_error.text = "您输入的手机号码位数不正确"
                    return
                }
                if (!RegexUtils.isMobileSimple(account)) {
                    tv_account_error.text = "请输入正确的手机号码"
                    return
                }
                tv_account_error.text = ""
            }

        })

        //密码输入框
        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = s.toString().trim()
                if (password.length < 6) {
                    tv_password_error.text = "至少输入6位长度的密码"
                    return
                }
                tv_password_error.text = ""
            }
        })

        //显示隐藏密码
        cb_hide_password.setOnCheckedChangeListener { _, isChecked ->
            et_password.inputType = if (isChecked) {
                InputType.TYPE_CLASS_TEXT
            } else {
                InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        //登录
        btn_login.setOnClickListener {
            val account = et_account.text.toString().trim()
            val password = et_password.text.toString().trim()
            val code = et_code.text.toString().trim()

            with(tv_account_error.text.toString()) {
                if (!TextUtils.isEmpty(this)) {
                    showToast(this)
                    return@setOnClickListener
                }
            }

            with(tv_password_error.text.toString()) {
                if (!TextUtils.isEmpty(this)) {
                    showToast(this)
                    return@setOnClickListener
                }
            }

            if (ll_code.isVisible && TextUtils.isEmpty(code)) {
                showToast("验证码不能为空")
                return@setOnClickListener
            }
            mPresenter.login(account, password, mCode.toString(), code)
        }

        //获取验证码
        btn_code.setOnClickListener {
            val account = et_account.text.toString().trim()
            with(tv_account_error.text.toString()) {
                if (!TextUtils.isEmpty(this)) {
                    showToast(this)
                    return@setOnClickListener
                }
            }
            mPresenter.getCode(account, ApiDomain.BASE_URL + ApiConfig.LOGIN)
        }
    }

    //登陆成功
    override fun loginSuccess(data: LoginBean) {

        SPUtils.saveData(SPConst.SP_LOGIN_DATA, data)
        SPUtils.saveParam(SPConst.SP_TOKEN, data.tokenResultBO.access_token)
        SPUtils.saveParam(SPConst.SP_USER_ID, data.centerUserMain.id)
        SPUtils.saveParam(SPConst.SP_IS_LOGIN, true)
        val bundle = Bundle()
        bundle.putBoolean("fromLogin", true)
        startActivity(MainActivity::class.java, bundle)
        finish()
    }

    //登陆失败
    override fun loginFailWithCode(code: Int) {
        ll_code.visibility = View.VISIBLE
        this.mCode = code
        when (code) {
            510 -> {
                et_code.hint = "请输入谷歌验证码"
            }
            520 -> {
                et_code.hint = "请输入短信验证码"
            }
            540 -> {
                et_code.hint = "请输入邮箱验证码"
            }

            else -> {
            }
        }
    }


    //获取验证码成功
    override fun getCodeSuccess() {
        if (mTimer == null) {
            mTimer = object : CountDownTimer(60 * 1000, 1000) {
                override fun onFinish() {
                    btn_code.isClickable = true
                    btn_code.text = "获取验证码"
                    if (mTimer != null) {
                        mTimer!!.cancel()
                        mTimer = null
                    }
                }

                override fun onTick(millisUntilFinished: Long) {
                    btn_code.isClickable = false
                    btn_code.text = String.format("%sS后重新获取", millisUntilFinished / 1000)
                }
            }
        }
        mTimer?.start()
    }

    //onStop,关闭timer
    override fun onStop() {
        super.onStop()
        if (mTimer != null) {
            mTimer?.cancel()
            mTimer = null
        }
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