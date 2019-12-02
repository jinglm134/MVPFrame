package com.project.mvpframe.base

/**
 * @CreateDate 2019/11/28 18:13
 * @Author jaylm
 */
open class BasePresenter<M : BaseModel, V : IBaseView> {
    private var mView: V? = null
    private var mModel: M? = null
    fun setMV(m: M, v: V) {
        mModel = m
        mView = v
    }
//    fun init(v: V) {
//        mView = v
//    }
}