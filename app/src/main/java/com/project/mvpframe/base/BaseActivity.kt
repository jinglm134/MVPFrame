package com.project.mvpframe.base

import android.content.Intent
import android.os.Bundle
import com.project.mvpframe.ui.activity.MainActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * @CreateDate 2019/11/28 18:18
 * @Author jaylm
 */
abstract class BaseActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }
    }
}