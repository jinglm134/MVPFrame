package com.project.mvpframe.bean

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.project.mvpframe.ui.common.adapter.ProvinceSheetAdapter

/**
 * 省市区
 * @CreateDate 2019/12/18 10:12
 * @Author jaylm
 */
data class ProvinceBean(
        val children: List<City>,
        val label: String,
        val value: String
) : BaseBean, MultiItemEntity, AbstractExpandableItem<City>() {
    override fun getLevel(): Int = 0
    override fun getItemType(): Int = ProvinceSheetAdapter.TYPE_PROVINCE
}

data class City(
        val children: List<Area>,
        val label: String,
        val value: String
) : BaseBean, MultiItemEntity, AbstractExpandableItem<Area>() {
    override fun getLevel(): Int = 1
    override fun getItemType(): Int = ProvinceSheetAdapter.TYPE_CITY
}

data class Area(
        val label: String,
        val value: String
) : BaseBean, MultiItemEntity {
    override fun getItemType(): Int = ProvinceSheetAdapter.TYPE_AREA
}