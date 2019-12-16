package com.project.mvpframe.base

import android.content.Context
import com.project.mvpframe.net.BaseObserver
import com.project.mvpframe.net.RxHelper
import com.project.mvpframe.util.helper.ClassReflectHelper

/**
 * @CreateDate 2019/11/28 18:13
 * @Author jaylm
 */
open class BasePresenter<M : BaseModel, V : IBaseView> {
    lateinit var mView: V
    lateinit var mModel: M
    protected lateinit var mContext: Context

    fun init(context: Context, v: V) {
        this.mContext = context
        mModel = ClassReflectHelper.getT(this, 0)
        //view 因为是接口,通过反射拿不到对象，所以需要设置
        mView = v
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
                    mView.successOfgetCode()
                }
            })
    }
}