package com.project.mvpframe.base

import android.content.Context
import com.project.mvpframe.net.BaseObserver
import com.project.mvpframe.net.RxHelper

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

    open fun getCode(
        mobile: String,
        noteType: String
    ) {
        mModel.getCode(mobile, noteType)
            .compose(RxHelper.observableIO2Main(mContext))
            .subscribe(object : BaseObserver<String>(mContext) {
                override fun onSuccess(data: String) {
                    mView.showToast(data)
                    mView.sucessOfgetCode()
                }
            })
    }
}