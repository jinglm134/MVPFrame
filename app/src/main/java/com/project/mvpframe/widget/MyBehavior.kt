package com.project.mvpframe.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.coordinatorlayout.widget.CoordinatorLayout

import com.google.android.material.appbar.AppBarLayout

/**
 * @CreateDate 2020/3/18 16:48
 * @Author jaylm
 */
class MyBehavior : AppBarLayout.Behavior {

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        return if (MotionEvent.ACTION_MOVE == ev.action) {
            false
        } else super.onInterceptTouchEvent(parent, child, ev)

    }

    constructor() : super()
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

}
