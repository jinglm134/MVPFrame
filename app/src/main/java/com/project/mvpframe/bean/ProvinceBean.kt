package com.project.mvpframe.bean

import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode

/**
 * 省市区
 * @CreateDate 2019/12/18 10:12
 * @Author jaylm
 */
data class ProvinceBean(
    val children: MutableList<BaseNode>,
    val label: String,
    val value: String
) : BaseBean, BaseExpandNode() {

    override val childNode: MutableList<BaseNode>
        get() = children

    init {
        isExpanded = false
    }
}

data class City(
    val children: MutableList<BaseNode>,
    val label: String,
    val value: String
) : BaseBean, BaseExpandNode() {
    override val childNode: MutableList<BaseNode>
        get() = children

    init {
        isExpanded = false
    }
}

data class Area(
    val label: String,
    val value: String
) : BaseBean, BaseNode() {
    override val childNode: MutableList<BaseNode>?
        get() = null

}