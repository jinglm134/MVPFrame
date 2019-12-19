package com.project.mvpframe.ui.common.presenter

import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.net.BaseObserver
import com.project.mvpframe.net.RxHelper
import com.project.mvpframe.ui.common.model.CodeModel
import com.project.mvpframe.ui.common.view.ICodeView

/**
 * @CreateDate 2019/12/19 14:54
 * @Author jaylm
 */
open class CodePresenter<m : CodeModel, v : ICodeView> : BasePresenter<m, v>() {

    open fun getCode(
        mobile: String,
        noteType: String
    ) {
        mModel.getCode(mobile, noteType)
            .compose(RxHelper.observableIO2Main(mContext))
            .subscribe(object : BaseObserver<String>(mContext) {
                override fun onSuccess(data: String) {
                    mView.showToast(data)
                    mView.getCodeSuccess()
                }
            })
    }
}