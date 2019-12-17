package com.project.mvpframe.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.project.mvpframe.app.MvpApp

/**
 * @CreateDate 2019/12/6 14:38
 * @Author jaylm
 */
object ToastUtils {
    private var sToast: Toast? = null
    private var isJumpWhenMore: Boolean = true

    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     *
     * `true`: 弹出新吐司<br></br>`false`: 只修改文本内容
     *
     * 如果为`false`的话可用来做显示任意时长的吐司
     */
    fun init(isJumpWhenMore: Boolean) {
        ToastUtils.isJumpWhenMore = isJumpWhenMore
    }


    /**
     * 显示短时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    fun showShortToast(text: CharSequence, context: Context = MvpApp.getInstance()) {
        showToast(context, text, Toast.LENGTH_SHORT)
    }


    /**
     * 显示长时吐司
     *
     * @param context 上下文
     * @param text    文本
     */
    fun showLongToast(text: CharSequence, context: Context = MvpApp.getInstance()) {
        showToast(context, text, Toast.LENGTH_LONG)
    }


    /**
     * 显示吐司
     *
     * @param context  上下文
     * @param text     文本
     * @param duration 显示时长
     */
    @SuppressLint("ShowToast")
    private fun showToast(context: Context, text: CharSequence, duration: Int) {
//        if (!BuildConfig.DEBUG) {
//            return
//        }
        if (isJumpWhenMore) cancelToast()
        if (sToast == null) {
            sToast = Toast.makeText(context.applicationContext, text, duration)
        } else {
            sToast!!.setText(text)
            sToast!!.duration = duration
        }
        sToast!!.show()
    }


    /**
     * 取消吐司显示
     */
    fun cancelToast() {
        if (sToast != null) {
            sToast!!.cancel()
            sToast = null
        }
    }
}