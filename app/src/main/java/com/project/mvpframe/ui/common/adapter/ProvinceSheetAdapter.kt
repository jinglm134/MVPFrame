package com.project.mvpframe.ui.common.adapter

import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.project.mvpframe.R
import com.project.mvpframe.bean.Area
import com.project.mvpframe.bean.City
import com.project.mvpframe.bean.ProvinceBean
import com.project.mvpframe.util.SizeUtils
import com.project.mvpframe.util.UShape

/**
 * 省市区适配器
 * @CreateDate 2019/12/18 10:16
 * @Author jaylm
 */

class ProvinceSheetAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(null) {

    companion object {
        const val TYPE_PROVINCE = 0x12
        const val TYPE_CITY = 0x13
        const val TYPE_AREA = 0x14
    }

    /*  private var provincePos = -1//当前展开省的下标
      private var provinceCount = 0//当前展开省拥有的的子类数量
      private var cityPos = -1//当前展开市的下标
      private var cityCount = 0//当前展开市拥有的的子类数量*/

    init {
        addItemType(TYPE_PROVINCE, R.layout.item_sheet_province)
        addItemType(TYPE_CITY, R.layout.item_sheet_province)
        addItemType(TYPE_AREA, R.layout.item_sheet_province)
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        when (helper.itemViewType) {
            TYPE_PROVINCE -> {
                val province = item as ProvinceBean
                helper.setText(R.id.tv_content, province.label)
                        .setTextColor(R.id.tv_content, UShape.getColor(R.color.black))
                        .setBackgroundRes(R.id.ll_root, R.drawable.selector_address_province)
                helper.getView<View>(R.id.ll_root).setPadding(0, 0, 0, 0)

                /* helper.itemView.setOnClickListener {
                     var position = helper.adapterPosition//当前点击位置

                     if (provincePos >= 0 && provincePos != position) {
                         //如果有一级列表项展开,并且展开位置不是当前点击位置,则关闭原一级列表展开项
                         if (cityPos >= 1) {
                             //如果有二级列表项展开,先关闭二级列表项
                             collapse(cityPos)
                             provinceCount += cityCount
                             cityPos = -1
                             cityCount = 0
                         }
                         collapse(provincePos)
                         if (position > provincePos) {
                             //如果当前点击位置大于原一级列表展开项的位置,由于原一级列表展开项被关闭,当前位置需要减去一级列表展开项的子项数量
                             position -= provinceCount
                         }
                     }

                     if (!province.isExpanded) {
                         //如果当前点击项未展开，则展开当前点击项,重置展开项位置和展开项数量
                         expand(position)
                         provincePos = position
                         provinceCount = province.children.size
                     } else {
                         collapse(position)
                         provincePos = -1
                         provinceCount = 0
                     }
                 }*/

            }
            TYPE_CITY -> {
                val city = item as City
                helper.setText(R.id.tv_content, city.label)
                        .setTextColor(R.id.tv_content, UShape.getColor(R.color.black_3))
                        .setBackgroundRes(R.id.ll_root, R.drawable.selector_address_city)
                helper.getView<View>(R.id.ll_root).setPadding(SizeUtils.dp2px(20F), 0, 0, 0)

                /* helper.itemView.setOnClickListener {
                     var position = helper.adapterPosition
                     if (cityPos >= 0 && cityPos != position) {
                         collapse(cityPos)
                         if (position > cityPos) {
                             position -= cityCount
                         }
                     }

                     if (!city.isExpanded) {
                         expand(position)
                         cityPos = position
                         cityCount = city.children.size
                     } else {
                         collapse(position)
                         cityPos = -1
                         cityCount = 0
                     }
                 }*/
            }
            TYPE_AREA -> {
                val area = item as Area
                helper.setText(R.id.tv_content, area.label)
                        .setTextColor(R.id.tv_content, UShape.getColor(R.color.black_6))
                        .setBackgroundRes(R.id.ll_root, R.drawable.selector_address_area)
                helper.getView<View>(R.id.ll_root).setPadding(SizeUtils.dp2px(40F), 0, 0, 0)
            }
        }

    }

}