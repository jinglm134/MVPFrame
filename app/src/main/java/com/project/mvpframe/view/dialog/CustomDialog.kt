package com.project.mvpframe.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.project.mvpframe.R
import com.project.mvpframe.util.UShape

/**
 * @CreateDate 2019/12/13 14:35
 * @Author jaylm
 */
class CustomDialog(private val mContext: Context) {

    companion object {
        const val CONTENT_TEXT_TYPE = 0//default,文本框
        const val CONTENT_EDIT_TYPE = 1//可选择,输入框
    }

    private var mDialog: Dialog? = null
    private var mDivideLine: ImageView? = null
    private var alreadyHaveOneBtn: Boolean = false
    private val mCancelListener = View.OnClickListener { }

    init {
        initDialog()
        setListener()
    }

    private fun initDialog() {
        mDialog = Dialog(mContext, R.style.BaseDialogStyle)
        mDialog!!.setCancelable(false)
        mDialog!!.setContentView(R.layout.dialog_custom)
        mDivideLine = mDialog!!.findViewById(R.id.center_line)
        val view = mDialog!!.findViewById<View>(R.id.dialog_layout)
        UShape.setBackgroundDrawable(
            UShape.getCornerDrawable(UShape.getColor(R.color.white), 10),
            view
        )
    }

    private fun setListener() {
        mDialog!!.setOnKeyListener { dialog, keyCode, event ->
            if (KeyEvent.KEYCODE_BACK == keyCode && event.action == KeyEvent.ACTION_DOWN) {
                dialog.dismiss()
                return@setOnKeyListener true
            }
            false
        }
    }

//    public void setTitle(int title) {
//        if (mContext != null) {
//            setTitle(mContext.getString(title));
//        }
//    }
//
//    public void setTitle(String title) {
//        TextView textView = mDialog.findViewById(R.id.tv_dialog_title);
//        textView.setText(title);
//    }


    fun setMessage(message: String, type: Int) {
        val textView = mDialog!!.findViewById<TextView>(R.id.tx_dialog_content)
        val editText = mDialog!!.findViewById<EditText>(R.id.et_dialog_content)
        when (type) {
            CONTENT_TEXT_TYPE -> {
                textView.visibility = View.VISIBLE
                editText.visibility = View.GONE
                textView.text = message
            }
            CONTENT_EDIT_TYPE -> {
                editText.visibility = View.VISIBLE
                textView.visibility = View.GONE
                editText.hint = message
            }
        }
    }

    fun addPositiveButton(title: String, listener: View.OnClickListener) {

        val layout = mDialog!!.findViewById<LinearLayout>(R.id.dialog_footer)
        layout.visibility = View.VISIBLE
        val btn_ok = mDialog!!.findViewById<Button>(R.id.btn_dialog_ok)
        btn_ok.visibility = View.VISIBLE
        btn_ok.text = title
        btn_ok.setOnClickListener { v ->
            listener.onClick(v)
            dismiss()
        }
        if (alreadyHaveOneBtn) {
            mDivideLine!!.visibility = View.VISIBLE
        } else {
            alreadyHaveOneBtn = true
        }
    }

    fun addPositiveButton(title: String, listener: OnEditClickListener) {

        val layout = mDialog!!.findViewById<LinearLayout>(R.id.dialog_footer)
        layout.visibility = View.VISIBLE
        val btn_ok = mDialog!!.findViewById<Button>(R.id.btn_dialog_ok)
        btn_ok.visibility = View.VISIBLE
        btn_ok.text = title
        btn_ok.setOnClickListener { v ->

            val editText = mDialog!!.findViewById<EditText>(R.id.et_dialog_content)
            listener.onPositiveClick(v, editText.text.toString().trim { it <= ' ' })
            dismiss()
        }
        if (alreadyHaveOneBtn) {
            mDivideLine!!.visibility = View.VISIBLE
        } else {
            alreadyHaveOneBtn = true
        }
    }

    fun addCancelButton(title: String, listener: View.OnClickListener) {

        val layout = mDialog!!.findViewById<LinearLayout>(R.id.dialog_footer)
        layout.visibility = View.VISIBLE
        val btn_cancel = mDialog!!.findViewById<Button>(R.id.btn_dialog_cancel)
        btn_cancel.visibility = View.VISIBLE
        btn_cancel.text = title
        btn_cancel.setOnClickListener { v ->
            listener.onClick(v)
            dismiss()
        }
        if (alreadyHaveOneBtn) {
            mDivideLine!!.visibility = View.VISIBLE
        } else {
            alreadyHaveOneBtn = true
        }
    }

    fun addCancelButton(title: String) {
        addCancelButton(title, mCancelListener)
    }

    fun show() {
        if (mDialog != null) {
            if (mContext is Activity && mContext.isFinishing) {
                return
            }
            mDialog!!.show()
        }
    }

    private fun dismiss() {
        if (mDialog != null) {
            mDialog!!.dismiss()
        }
    }


    interface OnEditClickListener {
        fun onPositiveClick(view: View, text: String)
    }

}