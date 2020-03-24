package com.project.mvpframe.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.entity.node.BaseNode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.mvpframe.R
import com.project.mvpframe.bean.address.ProvinceBean
import com.project.mvpframe.ui.common.adapter.ProvinceSheetAdapter
import com.project.mvpframe.util.GsonUtils
import com.project.mvpframe.util.UShape
import com.project.mvpframe.widget.decoration.LinearDecoration
import kotlin.properties.Delegates

/**
 * 省市区选择器
 * @CreateDate 2019/12/18 10:03
 * @Author jaylm
 */
@SuppressLint("InflateParams")
class ProvinceBottomSheet(
    private val mContext: Context,
    private val mListener: OnSheetItemClickListener? = null
) : AppCompatDialog(mContext, R.style.BaseDialogStyle) {

    private var mAdapter: ProvinceSheetAdapter = ProvinceSheetAdapter()
    private var mData = arrayListOf<BaseNode>()
    private var mRoot by Delegates.notNull<View>()
    private var tvCancel by Delegates.notNull<View>()
    private var recyclerView by Delegates.notNull<RecyclerView>()

    abstract class OnSheetItemClickListener {
        abstract fun onSheetItemClick(data: String)
        internal fun onSheetCancelClick() {}
    }

    init {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mRoot = inflater.inflate(R.layout.layout_bottom_sheet, null, false)
        mRoot.minimumWidth = 10000
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        val lp = window!!.attributes
        lp.x = 0
        lp.y = -1000
        lp.gravity = Gravity.BOTTOM
        onWindowAttributesChanged(lp)

        initView()
        setListener()
        setContentView(mRoot)
    }


    private fun initView() {
        mData.addAll(
            Gson().fromJson<ArrayList<ProvinceBean>>(
                GsonUtils.getJson(mContext, "address.json"),
                object : TypeToken<ArrayList<ProvinceBean>>() {}.type
            )
        )
        tvCancel = mRoot.findViewById(R.id.tv_cancel)
        recyclerView = mRoot.findViewById(R.id.recyclerView)
        val llContent = mRoot.findViewById<View>(R.id.ll_content)
        UShape.setBackgroundDrawable(
            UShape.getPressedDrawable(
                UShape.getColor(R.color.white),
                UShape.getColor(R.color.black_f0),
                4
            ), tvCancel
        )
        UShape.setBackgroundDrawable(
            UShape.getCornerDrawable(UShape.getColor(R.color.white), 4),
            llContent
        )

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
                mListener?.onSheetCancelClick()
                return@setOnKeyListener true
            }
            false
        }

        setOnCancelListener {
            mListener?.onSheetCancelClick()
        }

        tvCancel.setOnClickListener {
            dismiss()
            mListener?.onSheetCancelClick()
        }

        mAdapter.setOnAreaClickListener(object : ProvinceSheetAdapter.OnAreaClickListener {
            override fun OnAreaClickListener(
                province: String,
                city: String,
                area: String,
                areaCode: String
            ) {
                dismiss()
                mListener?.run {
                    onSheetItemClick(
                        if (province == city) {
                            "$city$area"
                        } else {
                            "$province$city$area"
                        }
                    )
                }
            }

        })

    }

}