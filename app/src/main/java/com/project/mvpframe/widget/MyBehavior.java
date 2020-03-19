package com.project.mvpframe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

/**
 * @CreateDate 2020/3/18 16:48
 * @Author jaylm
 */
public class MyBehavior extends AppBarLayout.Behavior {

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child, @NonNull MotionEvent ev) {
        if (MotionEvent.ACTION_MOVE == ev.getAction()) {
            return false;
        }

        return super.onInterceptTouchEvent(parent, child, ev);
    }


    public MyBehavior() {
        super();
    }

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
