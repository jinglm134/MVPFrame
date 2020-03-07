package com.project.mvpframe.app

import android.app.Application
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

}