package com.project.mvpframe.bean

/**
 * @CreateDate 2019/12/2 15:09
 * @Author jaylm
 */
data class PrizeListBean(
    val endRow: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val isFirstPage: Boolean,
    val isLastPage: Boolean,
    val list: List<ListBean>,
    val navigateFirstPage: Int,
    val navigateLastPage: Int,
    val navigatePages: Int,
    val navigatepageNums: List<Int>,
    val nextPage: Int,
    val pageNum: Int,
    val pageSize: Int,
    val pages: Int,
    val prePage: Int,
    val size: Int,
    val startRow: Int,
    val total: Int
)

data class ListBean(
    val endTime: String,
    val eunit: Int,
    val id: String,
    val picture: String,
    val status: String,
    val type: Int
)