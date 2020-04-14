package com.project.mvpframe.net

import android.content.Context
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.RxActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment
import com.trello.rxlifecycle2.components.support.RxFragmentActivity
import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * RxHelper调度类, 用于网络请求的的调度线程和执行线程，以及生命周期绑定
 * @CreateDate 2019/12/2 17:19
 * @Author jaylm
 */
object RxHelper {

    //设置线程
    fun <T> observableIO2Main(context: Context): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            val observable: Observable<T> =
                upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            composeContext(context, observable)
        }
    }

    fun <T> observableIO2Main(fragment: RxFragment): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.bindToLifecycle<T>())
        }
    }

    fun <T> flowableIO2Main(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    //绑定生命周期，防止内存泄露
    private fun <T> composeContext(context: Context,
                                   observable: Observable<T>): ObservableSource<T> {
        return when (context) {
            is RxActivity -> observable.compose(context.bindUntilEvent(ActivityEvent.DESTROY))
            is RxFragmentActivity -> observable.compose(context.bindUntilEvent(ActivityEvent.DESTROY))
            is RxAppCompatActivity -> observable.compose(context.bindUntilEvent(ActivityEvent.DESTROY))
            is RxFragment -> observable.compose(context.bindUntilEvent(FragmentEvent.DESTROY))
            /* is com.trello.rxlifecycle2.components.RxFragment -> observable.compose(
                 context.bindUntilEvent(
                     FragmentEvent.DESTROY
                 )
             )*/
            else -> observable
        }
    }
}