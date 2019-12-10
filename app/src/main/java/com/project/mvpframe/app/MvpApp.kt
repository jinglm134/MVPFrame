package com.project.mvpframe.app

import android.app.Application

/**
 * @CreateDate 2019/12/9 14:43
 * @Author jaylm
 */
class MvpApp : Application() {

    companion object {
        private lateinit var mApp: MvpApp
        fun getInstance(): Application {
            return mApp
        }
    }

    override fun onCreate() {
        super.onCreate()
        mApp = this
    }

}