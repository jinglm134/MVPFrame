package com.project.mvpframe.bean

/**
 * @CreateDate 2019/12/19 16:19
 * @Author jaylm
 */
data class BannerBean(val app: List<AppBean>) : BaseBean

data class AppBean(
    val imageUrl: String,
    val pointingUrl: String,
    val sort: Int,
    val type: Int,
    val annoId: String,
    val title: String
) : BaseBean