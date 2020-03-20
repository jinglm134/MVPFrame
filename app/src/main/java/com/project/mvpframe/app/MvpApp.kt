package com.project.mvpframe.app

import android.app.Application
import com.project.mvpframe.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlin.properties.Delegates


/**
 * @CreateDate 2019/12/9 14:43
 * @Author jaylm
 */
class MvpApp : Application() {

    companion object {
        var instance: MvpApp by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    init {
//        设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            ClassicsHeader(context)
                .setTextSizeTime(10f)
                .setTextSizeTitle(12f)
                .setDrawableSize(22f)
//                .setTimeFormat(DynamicTimeFormat("上次更新 %s"))
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            ClassicsFooter(context)
                .setTextSizeTitle(12f)
                .setDrawableSize(22f)
        }

    }

}