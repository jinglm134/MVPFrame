package com.project.mvpframe.ui.common.adapter

import android.view.View
import com.chad.library.adapter.base.BaseNodeAdapter
import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode
import com.chad.library.adapter.base.provider.BaseNodeProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
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

class ProvinceSheetAdapter : BaseNodeAdapter() {
    override fun getItemType(data: List<BaseNode>, position: Int): Int {
        return when (data[position]) {
            is ProvinceBean -> 0
            is City -> 1
            is Area -> 2
            else -> -1
        }
    }



    init {
        // 注册Provider
        // 需要占满一行的，使用此方法（例如section）
        addFullSpanNodeProvider(ProvinceProvider())
        // 普通的item provider
        addNodeProvider(CityProvider())
        addNodeProvider(AreaProvider())
        // 脚布局的 provider
//        addFooterNodeProvider(RootFooterNodeProvider())
    }

    inner class ProvinceProvider : BaseNodeProvider() {

        override val itemViewType: Int
            get() = 0

        override val layoutId: Int
            get() = R.layout.item_sheet_province

        override fun convert(helper: BaseViewHolder, data: BaseNode) {
            val entity = data as ProvinceBean
            helper.setText(R.id.tv_content, entity.label)
                .setTextColor(R.id.tv_content, UShape.getColor(R.color.black_3))
                .setBackgroundResource(R.id.ll_root, R.drawable.selector_address_province)
            helper.getView<View>(R.id.ll_root).setPadding(0, 0, 0, 0)
        }

        override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
            super.onClick(helper, view, data, position)
            if ((data as BaseExpandNode).isExpanded) {
                // 折叠某一个位置的Node
                getAdapter()?.collapseAndChild(position)
            } else {
                // 展开某一位置的Node
                getAdapter()?.expand(position)
            }

        }
    }

    inner class CityProvider : BaseNodeProvider() {

        override val itemViewType: Int
            get() = 1

        override val layoutId: Int
            get() = R.layout.item_sheet_province

        override fun convert(helper: BaseViewHolder, data: BaseNode) {
            val entity = data as City
            helper.setText(R.id.tv_content, entity.label)
                .setTextColor(R.id.tv_content, UShape.getColor(R.color.black_6))
                .setBackgroundResource(R.id.ll_root, R.drawable.selector_address_city)
            helper.getView<View>(R.id.ll_root).setPadding(SizeUtils.dp2px(20F), 0, 0, 0)
        }

        override fun onClick(helper: BaseViewHolder, view: View, data: BaseNode, position: Int) {
            super.onClick(helper, view, data, position)
            getAdapter()?.expandOrCollapse(position)
        }
    }

    inner class AreaProvider : BaseNodeProvider() {

        override val itemViewType: Int
            get() = 2

        override val layoutId: Int
            get() = R.layout.item_sheet_province

        override fun convert(helper: BaseViewHolder, data: BaseNode) {
            val entity = data as Area
            helper.setText(R.id.tv_content, entity.label)
                .setTextColor(R.id.tv_content, UShape.getColor(R.color.black_9))
                .setBackgroundResource(R.id.ll_root, R.drawable.selector_address_area)
            helper.getView<View>(R.id.ll_root).setPadding(SizeUtils.dp2px(40F), 0, 0, 0)
        }
    }
}