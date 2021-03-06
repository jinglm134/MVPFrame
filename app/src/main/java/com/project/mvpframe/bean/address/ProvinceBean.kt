package com.project.mvpframe.bean.address

import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode
import com.project.mvpframe.bean.IBaseBean

/**
 * 省市区
 * @CreateDate 2019/12/18 10:12
 * @Author jaylm
 */
data class ProvinceBean(
    val children: ArrayList<City>,
    val label: String,
    val value: String
) : IBaseBean, BaseExpandNode() {

    override val childNode: MutableList<BaseNode>
        get() = getChild()

    init {
        isExpanded = false
    }

    private fun getChild(): MutableList<BaseNode> {
        val child = mutableListOf<BaseNode>()
        child.addAll(children)
        return child
    }
}

data class City(
    val children: ArrayList<Area>,
    val label: String,
    val value: String
) : IBaseBean, BaseExpandNode() {
    override val childNode: MutableList<BaseNode>
        get() = getChild()

    init {
        isExpanded = false
    }

    private fun getChild(): MutableList<BaseNode> {
        val child = mutableListOf<BaseNode>()
        child.addAll(children)
        return child
    }
}

data class Area(
    val label: String,
    val value: String
) : IBaseBean, BaseNode() {
    override val childNode: MutableList<BaseNode>?
        get() = null

}