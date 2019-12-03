package com.project.mvpframe.base

import android.content.Context

/**
 * @CreateDate 2019/11/28 18:13
 * @Author jaylm
 */
open class BasePresenter<M : BaseModel, V : IBaseView> {
    lateinit var mView: V
    lateinit var mModel: M
    protected lateinit var mContext: Context
    fun setMV(m: M, v: V) {
        mModel = m
        mView = v
    }

    fun init(context: Context) {
        this.mContext = context
    }
}