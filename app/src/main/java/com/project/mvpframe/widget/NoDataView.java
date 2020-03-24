package com.project.mvpframe.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.mvpframe.R;


/**
 * @CreateDate 2019/12/18 10:03
 * @Author jaylm
 */
public class NoDataView extends FrameLayout {

    public NoDataView(@NonNull Context context) {
        this(context, null);

    }

    public NoDataView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoDataView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoDataView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_no_data, this);
    }

    public NoDataView setText(CharSequence charSequence) {
        TextView tvNotice = findViewById(R.id.tv_notice);
        tvNotice.setText(charSequence);
        return this;
    }
}
