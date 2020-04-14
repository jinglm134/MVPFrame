package com.project.mvpframe.bean

/**
 * @CreateDate 2019/12/19 16:19
 * @Author jaylm
 */
data class BannerBean(
    val app: List<App>,
    val wap: List<Wap>,
    val web: List<Web>
) : IBaseBean

data class App(
    val annoId: String,
    val imageUrl: String,
    val pointingUrl: String,
    val sort: Int,
    val type: Int
) : IBaseBean

data class Wap(
    val imageUrl: String,
    val pointingUrl: String,
    val sort: Int,
    val type: Int
) : IBaseBean

data class Web(
    val annoId: String,
    val imageUrl: String,
    val pointingUrl: String,
    val sort: Int,
    val type: Int
) : IBaseBean