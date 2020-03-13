package com.project.mvpframe.ui.user.view

import com.project.mvpframe.base.IBaseView
import com.project.mvpframe.bean.App

/**
 * @CreateDate 2019/12/19 15:58
 * @Author jaylm
 */
interface IHomeView : IBaseView {
    fun getBannerSuccess(data: List<App>)
}