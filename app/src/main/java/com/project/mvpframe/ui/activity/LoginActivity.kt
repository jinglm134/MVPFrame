package com.project.mvpframe.ui.activity

import android.os.CountDownTimer
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.google.gson.Gson
import com.project.mvpframe.BuildConfig
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.bean.LoginBean
import com.project.mvpframe.constant.ApiConfig
import com.project.mvpframe.constant.ApiDomain
import com.project.mvpframe.constant.SPConst
import com.project.mvpframe.ui.mvp.model.LoginModel
import com.project.mvpframe.ui.mvp.presenter.LoginPresenter
import com.project.mvpframe.ui.mvp.view.ILoginView
import com.project.mvpframe.util.RegexUtils
import com.project.mvpframe.util.SPUtils
import com.project.mvpframe.util.UShape
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录页面
 * @CreateDate 2019/12/2 15:32
 * @Author jaylm
 */
class LoginActivity : BaseActivity<LoginModel, LoginPresenter>(), ILoginView {

    private var mTimer: CountDownTimer? = null

    override fun initMVP() {
        mPresenter.setMV(mModel, this)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView(contentView: View) {
        //初始化view背景
        UShape.setBackgroundDrawable(
            UShape.getPressedDrawable(
                UShape.getColor(R.color.colorPrimary),
                UShape.getColor(R.color.blue),
                4
            ), btn_login
        )
        UShape.setBackgroundDrawable(
            UShape.getPressedDrawable(
                UShape.getColor(R.color.colorPrimary),
                UShape.getColor(R.color.blue),
                4
            ), btn_code
        )
        UShape.setBackgroundDrawable(
            UShape.getStrokeDrawable(
                UShape.getColor(R.color.black_c),
                UShape.getColor(R.color.white),
                4
            ), et_account
        )
        UShape.setBackgroundDrawable(
            UShape.getStrokeDrawable(
                UShape.getColor(R.color.black_c),
                UShape.getColor(R.color.white),
                4
            ), et_password
        )
        UShape.setBackgroundDrawable(
            UShape.getStrokeDrawable(
                UShape.getColor(R.color.black_c),
                UShape.getColor(R.color.white),
                4
            ), et_code
        )

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

            if (account.length != 11) {
                showToast("您输入的手机号码位数不正确")
                return@setOnClickListener
            }
            if (!RegexUtils.isMobileSimple(account)) {
                showToast("手机号码不规范")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                showToast("密码不能为空")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(code)) {
                showToast("验证码不能为空")
                return@setOnClickListener
            }
            mPresenter.login(account, password, BuildConfig.BASEAPI + ApiConfig.LOGIN, code)
        }

        //获取验证码
        btn_code.setOnClickListener {
            if (TextUtils.isEmpty(tv_account_error.text.toString().trim())) {
                mPresenter.getCode(
                    et_account.text.toString().trim(),
                    ApiDomain.BASE_URL + ApiConfig.LOGIN
                )
            }

        }
    }

    //登陆成功
    override fun successOfLogin(data: LoginBean) {
        SPUtils.getInstance(mActivity).saveParam(SPConst.SP_LOGIN_DATA, Gson().toJson(data))
        SPUtils.getInstance(mActivity).saveParam(SPConst.SP_TOKEN, data.tokenResultBO.access_token)
        SPUtils.getInstance(mActivity).saveParam(SPConst.SP_USER_ID, data.centerUserMain.id)
        SPUtils.getInstance(mActivity).saveParam(SPConst.SP_IS_LOGIN, true)
        startActivity(MainActivity::class.java)
    }


    //获取验证码成功
    override fun sucessOfgetCode() {
        super.sucessOfgetCode()
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
        mTimer!!.start()
    }

    //onStop,关闭timer
    override fun onStop() {
        super.onStop()
        if (mTimer != null) {
            mTimer!!.cancel()
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