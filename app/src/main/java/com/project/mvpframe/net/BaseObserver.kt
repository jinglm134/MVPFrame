package com.project.mvpframe.net

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.project.mvpframe.bean.BaseResponse
import com.project.mvpframe.util.ToastUtils
import com.project.mvpframe.view.dialog.ProgressDialog
import io.reactivex.observers.DisposableObserver

/**
 * @CreateDate 2019/12/3 10:52
 * @Author jaylm
 */
abstract class BaseObserver<T>(
    private var mContext: Context,
    private var mShowDialog: Boolean = true
) : DisposableObserver<BaseResponse<T>>() {

    private var dialog: ProgressDialog? = null

    override fun onStart() {
        super.onStart()
        if (isConnected(mContext)) {
            showDialog()
        } else {
            Toast.makeText(mContext, "暂无网络,请检查网络设置", Toast.LENGTH_SHORT).show()
            if (!isDisposed) {
                //取消订阅
                dispose()
            }
        }
    }

    override fun onNext(response: BaseResponse<T>) {
        val code = response.code
        val msg = response.message
        val data = response.data
        if (code == 200) {
            onSuccess(data)
        } else {
            onFailure(code, msg)
        }
    }

    //请求完成
    override fun onComplete() {
        if (!isDisposed) {
            dispose()
        }
        hideDialog()
        endRefresh()
    }

    //请求失败
    override fun onError(e: Throwable) {
        if (!isDisposed) {
            dispose()
        }
        hideDialog()
        endRefresh()

    }

    //请求成功的回调函数
    protected abstract fun onSuccess(data: T)

    //code!=200,即请求失败的回调,需要时可重写
    open fun onFailure(code: Int, msg: String) {
        ToastUtils.showShortToast(msg)
    }

    //开启网络请求动画
    private fun showDialog() {
        if (!mShowDialog) {
            return
        }
        if (dialog == null) {
            dialog = ProgressDialog(mContext)
        }
        dialog!!.show()
    }

    //隐藏网络请求动画
    private fun hideDialog() {
        if (!mShowDialog) {
            return
        }
        dialog?.dismiss()
        dialog = null
    }

    //如果需要取消刷新，重写该方法
    open fun endRefresh() {}

    //是否有网络
    @Suppress("DEPRECATION")
    private fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo ?: return false
        return info.isAvailable
    }
}