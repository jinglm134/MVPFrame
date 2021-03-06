package com.project.mvpframe.net

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.project.mvpframe.app.MvpApp
import com.project.mvpframe.util.ToastUtils
import com.project.mvpframe.widget.dialog.ProgressDialog
import io.reactivex.observers.DisposableObserver
import okhttp3.ResponseBody
import org.json.JSONObject

/**
 * @CreateDate 2019/12/3 10:52
 * @Author jaylm
 */
abstract class BaseObserverString(private var mContext: Context,
                                  private var mShowDialog: Boolean = true) :
    DisposableObserver<ResponseBody>() {

    private var dialog: ProgressDialog? = null

    override fun onStart() {
        super.onStart()
        if (isConnected()) {
            showDialog()
        } else {
            ToastUtils.showShortToast("暂无网络,请检查网络设置")
            if (!isDisposed) {
                //取消订阅
                dispose()
            }
        }
    }

    override fun onNext(response: ResponseBody) {
        val jsonObject = JSONObject(String(response.bytes()))
        val code = jsonObject.optInt("code")
        val msg = jsonObject.optString("message")
        val data = jsonObject.optString("data")
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
        ToastUtils.showShortToast(RxException.exceptionHandler(e))
    }

    //请求成功的回调函数
    protected abstract fun onSuccess(data: String)

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
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    //如果需要取消刷新，重写该方法
    protected fun endRefresh() {}

    //是否有网络
    @Suppress("DEPRECATION")
    private fun isConnected(): Boolean {
        val cm =
            MvpApp.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo ?: return false
        return info.isAvailable
    }
}