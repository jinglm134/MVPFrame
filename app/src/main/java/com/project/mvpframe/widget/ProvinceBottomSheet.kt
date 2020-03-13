package com.project.mvpframe.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.project.mvpframe.R
import com.project.mvpframe.bean.Area
import com.project.mvpframe.bean.City
import com.project.mvpframe.bean.ProvinceBean
import com.project.mvpframe.ui.common.adapter.ProvinceSheetAdapter
import com.project.mvpframe.util.UShape
import com.project.mvpframe.widget.decoration.LinearDecoration
import kotlinx.android.synthetic.main.layout_bottom_sheet.*

/**
 * 省市区选择器
 * @CreateDate 2019/12/18 10:03
 * @Author jaylm
 */
class ProvinceBottomSheet(private val mContext: Context,
                          private val mData: List<MultiItemEntity>,
                          private val mListener: OnSheetItemClickListener) : AppCompatDialog(mContext, R.style.BaseDialogStyle) {

    private var mAdapter: ProvinceSheetAdapter = ProvinceSheetAdapter()
    private var provinceStr: String = ""
    private var cityStr: String = ""
    private var areaStr: String = ""

    private var provincePos = -1//当前展开省的下标
    private var provinceCount = 0//当前展开省拥有的的子类数量
    private var cityPos = -1//当前展开市的下标
    private var cityCount = 0//当前展开市拥有的的子类数量

    abstract class OnSheetItemClickListener {
        abstract fun onSheetItemClick(data: String)
        internal fun onSheetCancelClick() {}
    }

    init {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        @SuppressLint("InflateParams") val layout = inflater.inflate(R.layout.layout_bottom_sheet, null, false) as LinearLayout
        layout.minimumWidth = 10000
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        val lp = window!!.attributes
        lp.x = 0
        lp.y = -1000
        lp.gravity = Gravity.BOTTOM
        onWindowAttributesChanged(lp)

        initView()
        setListener()
        setContentView(layout)
    }


    private fun initView() {
        UShape.setBackgroundDrawable(UShape.getPressedDrawable(UShape.getColor(R.color.white), UShape.getColor(R.color.black_f0), 4), tv_cancel)
        UShape.setBackgroundDrawable(UShape.getCornerDrawable(UShape.getColor(R.color.white), 4), ll_content)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(LinearDecoration(padding = 3))
        recyclerView.adapter = mAdapter
        mAdapter.setNewData(mData)
    }

    private fun setListener() {

        setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss()
                mListener.onSheetCancelClick()
                return@setOnKeyListener true
            }
            false
        }

        setOnCancelListener {
            mListener.onSheetCancelClick()
        }

        tv_cancel.setOnClickListener {
            dismiss()
            mListener.onSheetCancelClick()
        }

        mAdapter.setOnItemClickListener { adapter, _, position ->
            var mPosition = position
            val entity = adapter.data[mPosition] as MultiItemEntity
            when (entity.itemType) {

                ProvinceSheetAdapter.TYPE_PROVINCE -> {
                    val province = entity as ProvinceBean
                    provinceStr = province.label

                    if (cityPos >= 1) {
                        //如果有二级列表项展开,先关闭二级列表项
                        adapter.collapse(cityPos)
                        provinceCount += cityCount
                        cityPos = -1
                        cityCount = 0
                    }
                    if (provincePos >= 0 && provincePos != mPosition) {
                        //如果有一级列表项展开,并且展开位置不是当前点击位置,则关闭原一级列表展开项
                        adapter.collapse(provincePos)
                        if (mPosition > provincePos) {
                            //如果当前点击位置大于原一级列表展开项的位置,由于原一级列表展开项被关闭,当前位置需要减去一级列表展开项的子项数量
                            mPosition -= provinceCount
                        }
                    }

                    if (!province.isExpanded) {
                        //如果当前点击项未展开，则展开当前点击项,重置展开项位置和展开项数量
                        adapter.expand(mPosition)
                        provincePos = mPosition
                        provinceCount = province.children.size
                    } else {
                        adapter.collapse(mPosition)
                        provincePos = -1
                        provinceCount = 0
                    }
                    mListener.onSheetItemClick("")
                }

                ProvinceSheetAdapter.TYPE_CITY -> {
                    val city = entity as City
                    cityStr = city.label
                    if (cityPos >= 0 && cityPos != mPosition) {
                        adapter.collapse(cityPos)
                        if (mPosition > cityPos) {
                            mPosition -= cityCount
                        }
                    }

                    if (!city.isExpanded) {
                        adapter.expand(mPosition)
                        cityPos = mPosition
                        cityCount = city.children.size
                    } else {
                        adapter.collapse(mPosition)
                        cityPos = -1
                        cityCount = 0
                    }
                    mListener.onSheetItemClick("")
                }

                ProvinceSheetAdapter.TYPE_AREA -> {
                    dismiss()
                    areaStr = (entity as Area).label
                    mListener.onSheetItemClick("$provinceStr$cityStr$areaStr")
                }
            }

        }
    }
}