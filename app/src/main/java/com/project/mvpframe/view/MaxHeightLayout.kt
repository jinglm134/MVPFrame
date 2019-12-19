package com.project.mvpframe.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.project.mvpframe.R
import com.project.mvpframe.util.SizeUtils

/**
 * 最大高度Layout
 * @CreateDate 2019/12/18 9:48
 * @Author jaylm
 */
class MaxHeightLayout : FrameLayout {

    private var mMaxRatio: Float = 0.toFloat()// 优先级高
    private var mMaxHeight = 0f// 优先级低

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context, attrs, defStyleAttr)
        init()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyle: Int) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.MaxHeightView, defStyle, 0)
        mMaxRatio = ta.getFloat(R.styleable.MaxHeightView_heightRatio, 1.0f)
        ta.recycle()
    }

    private fun init() {
        val screenHeight = SizeUtils.getScreenHeight()
        mMaxHeight = if (mMaxHeight <= 0) {
            mMaxRatio * screenHeight
        } else {
            mMaxHeight.coerceAtMost(mMaxRatio * screenHeight)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight.toInt()
            MeasureSpec.UNSPECIFIED -> heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight.toInt()
            MeasureSpec.AT_MOST -> heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight.toInt()
        }

        val maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
            heightSize,
            heightMode
        )
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec)
    }
}