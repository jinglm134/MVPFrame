package com.project.mvpframe.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

/**
 * @CreateDate 2019/12/16 11:23
 * @Author jaylm
 */
@Suppress("DEPRECATION")
class Banner : Gallery, AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener, View.OnTouchListener {

    /**
     * 条目单击事件接口
     */
    private var mOnBannerItemClickListener: OnBannerItemClickListener? = null
    /**
     * 图片切换时间
     */
    private var mSwitchTime: Int = 0
    /**
     * 自动滚动的定时器
     */
    private var mTimer: Timer? = null
    /**
     * 圆点容器
     */
    private var lltDot: LinearLayout? = null
    /**
     * 当前选中的数组索引
     */
    private var curIndex = 0
    /**
     * 上次选中的数组索引
     */
    private var oldIndex = 0
    /**
     * 圆点选中时的背景ID
     */
    private var mFocusedId: Int = 0
    /**
     * 圆点正常时的背景ID
     */
    private var mNormalId: Int = 0

    /**
     * 图片网络路径数组
     */
    private lateinit var mUris: List<String>
    /**
     * ImageView
     */
    private var listImgs: MutableList<ImageView>? = null

    /**
     * 处理定时滚动任务
     */


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     * @param urls       图片的网络路径数组
     * @param ovalLayout 圆点容器 ,可为空
     * @param focusedId  圆点选中时的背景ID,圆点容器可为空写0
     * @param normalId   圆点正常时的背景ID,圆点容器为空写0
     * @param switchTime 图片切换时间(毫秒) ,0为不自动切换
     */
    @SuppressLint("ClickableViewAccessibility")
    fun start(
        urls: List<String>,
        ovalLayout: LinearLayout?,
        focusedId: Int = 0,
        normalId: Int = 0,
        switchTime: Int = 0
    ) {
        this.mUris = urls
        this.mSwitchTime = switchTime
        this.lltDot = ovalLayout
        this.mFocusedId = focusedId
        this.mNormalId = normalId

        /* 初始化图片组 */
        initImages()
        adapter = AdAdapter()
        this.onItemClickListener = this
        this.setOnTouchListener(this)
        this.onItemSelectedListener = this
        this.isSoundEffectsEnabled = false
        /* 动画时间 */
        this.setAnimationDuration(700)
        /*
         * 未选中项目的透明度 不包含spacing会导致onKeyDown()失效!!!
         * 失效onKeyDown()前先调用onScroll(null,1,0)可处理
         */
        this.setUnselectedAlpha(1f)
        /* 取靠近中间 图片数组的整倍数 */
        setSpacing(0)
        /* 默认选中中间位置为起始位置 */
        setSelection(count / 2 / listImgs!!.size * listImgs!!.size)
        isFocusableInTouchMode = true
        /* 初始化圆点 */
        initDotLayout()
        /* 开始自动滚动任务 */
        startTimer()
    }

    /**
     * 初始化图片组
     */
    private fun initImages() {
        /* 图片集合 */
        listImgs = ArrayList()
        val len = mUris.size
        for (i in 0 until len) {
            /* 实例化ImageView的对象 */
            val imageView = ImageView(context)
            /* 设置缩放方式 */
            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            if (mUris.isEmpty()) {
                /* 加载本地图片*/
//                imageView.setImageResource(mAdsId!![i])
                return
            } else {
                /*加载网络图片*/
                Glide.with(context)
                    .load(mUris[i])
                    //                        .error(R.mipmap.banner)
                    .into(imageView)
            }
            listImgs!!.add(imageView)
        }
    }

    /**
     * 初始化圆点
     */
    private fun initDotLayout() {
        if (lltDot == null) {
            return
        }
        lltDot!!.visibility = View.VISIBLE
        if (listImgs!!.size < 2) {
            /*如果只有一张图时不显示圆点容器*/
            lltDot!!.layoutParams.height = 0
        } else {
            lltDot!!.removeAllViews()
            /* 圆点的大小是 圆点窗口的 70%;*/
            val ovalHeight = (lltDot!!.layoutParams.height * 0.6).toInt()
            val ovalWidth = (lltDot!!.layoutParams.height * 0.6).toInt()
            /* 圆点的左右外边距是 圆点窗口的 20%;*/
            val ovalMargin = (lltDot!!.layoutParams.height * 0.2).toInt()
            val layoutParams = LinearLayout.LayoutParams(
                ovalWidth, ovalHeight
            )
            layoutParams.setMargins(ovalMargin, 0, ovalMargin, 0)
            for (i in listImgs!!.indices) {
                /* 圆点*/
                val v = View(context)
                v.layoutParams = layoutParams
                v.setBackgroundResource(mNormalId)
                lltDot!!.addView(v)
            }
            /*设置第一个圆点默认选中*/
            lltDot!!.getChildAt(0).setBackgroundResource(mFocusedId)
        }
    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val kEvent = if (isScrollingLeft(e1, e2)) {
            /*检查是否向左滑动,KeyEvent.KEYCODE_DPAD_LEFT*/
            KeyEvent.KEYCODE_DPAD_LEFT
        } else {
            /* 检查是否向右滑动,KeyEvent.KEYCODE_DPAD_RIGHT*/
            KeyEvent.KEYCODE_DPAD_RIGHT
        }
        onKeyDown(kEvent, null)
        return true

    }

    /**
     * 检查是否向左滑动
     */
    private fun isScrollingLeft(e1: MotionEvent, e2: MotionEvent): Boolean {
        return e2.x > e1.x + 50
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (MotionEvent.ACTION_UP == event.action || MotionEvent.ACTION_CANCEL == event.action) {
            /* 开始自动滚动任务*/
            startTimer()
        } else {
            /* 停止自动滚动任务*/
            stopTimer()
        }
        return false
    }

    /**
     * 图片切换事件
     */
    override fun onItemSelected(
        arg0: AdapterView<*>, arg1: View, position: Int,
        arg3: Long
    ) {
        curIndex = position % listImgs!!.size
        if (lltDot != null && listImgs!!.size > 1) {
            /* 切换圆点*/
            lltDot!!.getChildAt(oldIndex).setBackgroundResource(mNormalId)
            lltDot!!.getChildAt(curIndex).setBackgroundResource(mFocusedId)
            oldIndex = curIndex
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {}

    /**
     * 项目点击事件
     */
    override fun onItemClick(
        arg0: AdapterView<*>, arg1: View, position: Int,
        arg3: Long
    ) {
        if (mOnBannerItemClickListener != null) {
            mOnBannerItemClickListener!!.onBannerItemClick(curIndex)
        }
    }

    /**
     * 设置项目点击事件监听
     */
    fun setOnBannerItemClickListener(
        listener: OnBannerItemClickListener
    ) {
        mOnBannerItemClickListener = listener
    }

    /**
     * 停止自动滚动任务
     */
    private fun stopTimer() {
        if (mTimer != null) {
            mTimer!!.cancel()
            mTimer = null
        }
    }

    /**
     * 启动自动滚动任务 图片大于1张才滚动
     */
    private fun startTimer() {
        if (listImgs!!.size > 1 && mSwitchTime > 0) {
            if (mTimer == null) {
                mTimer = Timer()
            }
            mTimer!!.schedule(object : TimerTask() {
                override fun run() {
                    onScroll(null, null, 1f, 0f)
                    onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null)
                }
            }, mSwitchTime.toLong(), mSwitchTime.toLong())
        }
    }

    /**
     * 项目点击事件监听器接口
     */
    interface OnBannerItemClickListener {
        /**
         * @param curIndex 当前条目在数组中的下标
         */
        fun onBannerItemClick(curIndex: Int)
    }

    /**
     * 无限循环适配器
     */
    internal inner class AdAdapter : BaseAdapter() {

        override fun getCount(): Int {
            /*如果只有一张图时不滚动*/
            return if (listImgs!!.size < 2) {
                listImgs!!.size
            } else Integer.MAX_VALUE
        }

        override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
            return listImgs!![position % listImgs!!.size]
        }

        override fun getItem(position: Int): Any {
            return listImgs!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }
}