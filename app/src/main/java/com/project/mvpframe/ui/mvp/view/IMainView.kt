package com.project.mvpframe.ui.mvp.view

import com.project.mvpframe.base.IBaseView
import com.project.mvpframe.bean.PrizeListBean

/**
 * @CreateDate 2019/11/29 10:22
 * @Author jaylm
 */
interface IMainView :IBaseView{
    fun success(data:List<PrizeListBean>)
}