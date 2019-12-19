package com.project.mvpframe.ui.user.view

import com.project.mvpframe.base.IBaseView
import com.project.mvpframe.bean.Notice
import com.project.mvpframe.bean.PrizeListBean

/**
 * @CreateDate 2019/11/29 10:22
 * @Author jaylm
 */
interface IMainView : IBaseView {
    fun prizeSuccess(data: List<PrizeListBean>)
    fun noticeSuccess(data: List<Notice>)
}