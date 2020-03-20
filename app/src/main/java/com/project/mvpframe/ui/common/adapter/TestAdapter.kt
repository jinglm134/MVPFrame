package com.project.mvpframe.ui.common.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.project.mvpframe.R
import com.project.mvpframe.util.ToastUtils

/**
 * @CreateDate 2020/3/16 18:30
 * @Author jaylm
 */
class TestAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_sheet_province) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_content, item)
        helper.getView<View>(R.id.tv_content).setOnClickListener {
            ToastUtils.showShortToast(
                "adapterPosition${helper.adapterPosition}",
                mContext
            )
        }
    }
}