package com.project.mvpframe.util

import android.content.Context
import android.view.View
import com.project.mvpframe.view.CustomDialog

/**
 * @CreateDate 2019/12/13 14:43
 * @Author jaylm
 */
object DialogUtils {

    /**
     * 显示单个确定按钮的dialog
     *
     * @param context       Context
     * @param message       提示内容
     * @param clickListener 确定按钮的点击事件
     */
    fun showSingleDialog(context: Context, message: String, clickListener: View.OnClickListener) {
        showSingleDialog(context, message, "确认", clickListener)
    }

    /**
     * 显示单个按钮的dialog
     *
     * @param context         Context
     * @param message         提示内容
     * @param positiveBtnName 按钮的文本
     * @param clickListener   按钮的点击事件
     */
    fun showSingleDialog(
        context: Context,
        message: String,
        positiveBtnName: String,
        clickListener: View.OnClickListener
    ) {
        val builder = CustomDialog(context)
        builder.addPositiveButton(positiveBtnName, clickListener)
        builder.setMessage(message, CustomDialog.CONTENT_TEXT_TYPE)
        builder.show()
    }

    /**
     * 显示确定和取消两个按钮的dialog
     *
     * @param context       Context
     * @param message       提示内容
     * @param clickListener 确定按钮的点击事件
     */
    fun showTwoDialog(context: Context, message: String, clickListener: View.OnClickListener) {
        showTwoDialog(context, message, "确认", clickListener)
    }

    /**
     * 显示两个按钮(积极按钮  和  取消)的dialog
     *
     * @param context         Context
     * @param message         提示内容
     * @param positiveBtnName 积极按钮的文本
     * @param clickListener   积极按钮的点击事件
     */
    fun showTwoDialog(
        context: Context,
        message: String,
        positiveBtnName: String,
        clickListener: View.OnClickListener
    ) {
        val builder = CustomDialog(context)
        builder.addPositiveButton(positiveBtnName, clickListener)
        builder.setMessage(message, CustomDialog.CONTENT_TEXT_TYPE)
        builder.addCancelButton("取消")
        builder.show()
    }

    /**
     * 显示两个按钮(积极按钮  和  取消)的dialog
     *
     * @param context          Context
     * @param message          提示内容
     * @param positiveBtnName  积极按钮的文本
     * @param positiveListener 积极按钮的点击事件
     * @param cancelListener   取消按钮的点击事件
     */
    fun showTwoDialog(
        context: Context,
        message: String,
        positiveBtnName: String,
        positiveListener: View.OnClickListener,
        cancelListener: View.OnClickListener
    ) {
        val builder = CustomDialog(context)
        builder.addPositiveButton(positiveBtnName, positiveListener)
        builder.setMessage(message, CustomDialog.CONTENT_TEXT_TYPE)
        builder.addCancelButton("取消", cancelListener)
        builder.show()
    }

    /**
     * 显示带输入框的两个按钮(确定 和 取消)的dialog
     *
     * @param context          Context
     * @param message          提示内容
     * @param positiveListener 确定按钮的点击事件
     */
    fun showEditDialog(
        context: Context,
        message: String,
        positiveListener: CustomDialog.OnEditClickListener
    ) {
        val builder = CustomDialog(context)
        builder.addPositiveButton("确认", positiveListener)
        builder.setMessage(message, CustomDialog.CONTENT_EDIT_TYPE)
        builder.addCancelButton("取消")
        builder.show()
    }

    /**
     * 显示带输入框的两个按钮(积极按钮和取消)的dialog
     *
     * @param context          Context
     * @param message          提示内容
     * @param positiveBtnName  积极按钮的文本
     * @param positiveListener 积极按钮的点击事件
     * @param cancelListener   取消按钮的点击事件
     */
    fun showEditDialog(
        context: Context,
        message: String,
        positiveBtnName: String,
        positiveListener: CustomDialog.OnEditClickListener,
        cancelListener: View.OnClickListener
    ) {
        val builder = CustomDialog(context)
        builder.addPositiveButton(positiveBtnName, positiveListener)
        builder.setMessage(message, CustomDialog.CONTENT_EDIT_TYPE)
        builder.addCancelButton("取消", cancelListener)
        builder.show()
    }
}