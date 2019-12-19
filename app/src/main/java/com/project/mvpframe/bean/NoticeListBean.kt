package com.project.mvpframe.bean

/**
 * @CreateDate 2019/12/6 17:20
 * @Author jaylm
 */
data class NoticeListBean(
        val endRow: Int,
        val hasNextPage: Boolean,
        val hasPreviousPage: Boolean,
        val isFirstPage: Boolean,
        val isLastPage: Boolean,
        val list: List<Notice>,
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
) : BaseBean

data class Notice(
        val id: String,
        val lastChangeTime: String,
        val platform: Int,
        val publishTime: String,
        val sort: Int,
        val status: Int,
        val title: String,
        val topFlag: Int,
        val typeId: Int
) : BaseBean