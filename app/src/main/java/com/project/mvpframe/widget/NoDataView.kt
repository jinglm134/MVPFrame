package com.project.mvpframe.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView

import com.project.mvpframe.R


/**
 * @CreateDate 2019/12/18 10:03
 * @Author jaylm
 */
class NoDataView @TargetApi(Build.VERSION_CODES.LOLLIPOP)
constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : this(context, attrs, defStyleAttr, 0)

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_no_data, this)
    }

    fun setText(charSequence: CharSequence): NoDataView {
        val tvNotice = findViewById<TextView>(R.id.tv_notice)
        tvNotice.text = charSequence
        return this
    }
}
