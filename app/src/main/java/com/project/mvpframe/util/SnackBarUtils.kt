@file:Suppress("DEPRECATION")

package com.project.mvpframe.util

import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.Snackbar
import com.project.mvpframe.R

/**
 * @CreateDate 2019/11/28 15:31
 * @Author jaylm
 */
object SnackBarUtils {

    private var mSnackBar: Snackbar? = null
    /**
     * 设置snackbar文字和背景颜色
     *
     * @param  parent 父视图(CoordinatorLayout或者DecorView)
     * @param  text 文本
     * @param  textColor 文本颜色
     * @param  bgColor 背景色
     * @param  actionText 事件文本
     * @param  actionTextColor 事件文本颜色
     * @param  listener 监听器
     * @param  duration 显示时长
     */
    fun showSnackbar(
        parent: View,
        text: CharSequence,
        @ColorRes textColor: Int = R.color.black_3,
        @ColorRes bgColor: Int = R.color.black_e,
        actionText: CharSequence = "",
        @ColorRes actionTextColor: Int = R.color.blue,
        listener: View.OnClickListener? = null,
        duration: Int = Snackbar.LENGTH_SHORT
    ) {
        if (mSnackBar == null) {
            mSnackBar = Snackbar.make(parent, text, duration)
        }
        with(mSnackBar!!.view) {
            val textView = this.findViewById<TextView>(R.id.snackbar_text)
            textView.setTextColor(mSnackBar!!.context.resources.getColor(textColor))
            textView.textSize = 15f
            textView.setLines(1)
            textView.ellipsize = TextUtils.TruncateAt.END
            this.setBackgroundColor(mSnackBar!!.context.resources.getColor(bgColor))
        }
        if (actionText.isNotBlank() && listener != null) {
            mSnackBar!!.setActionTextColor(mSnackBar!!.context.resources.getColor(actionTextColor))
            mSnackBar!!.setAction(actionText, listener)
        }
        mSnackBar!!.duration = duration
        mSnackBar!!.show()
    }


    /**
     * 为SnackBar添加布局
     * <p>在show...SnackBar之后调用</p>
     *
     * @param layoutId 布局文件
     * @param index    位置(the position at which to add the child or -1 to add last)
     */

    fun addView(@LayoutRes layoutId: Int, index: Int) {
        if (mSnackBar != null) {
            val view = mSnackBar!!.view
            val layout = view as Snackbar.SnackbarLayout
            val child = LayoutInflater.from(view.getContext()).inflate(layoutId, null)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.CENTER_VERTICAL
            layout.addView(child, index, params)
        }
    }

    /**
     * 取消SnackBar显示
     */
    fun dismissSnackbar() {
        mSnackBar?.dismiss()
    }
}