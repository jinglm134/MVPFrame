package com.project.mvpframe.base

/**
 * @CreateDate 2019/11/28 18:13
 * @Author jaylm
 */
open class BasePresenter<V, M> {
    private var mView: V? = null
    private var mModel: M? = null
    fun setVM(v: V, m: M) {
        mView = v
        mModel = m
    }
}