package com.project.mvpframe.net

import android.content.Context
import com.trello.rxlifecycle2.android.ActivityEvent
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
 * @CreateDate 2019/12/2 15:17
 * @Author jaylm
 */

object RxHelper {

//    fun <T> observableIO2Main(context: Context): ObservableTransformer<T, T> {
//        return { upstream ->
//            val observable = upstream.observeOn(AndroidSchedulers.mainThread())
//            composeContext<T>(context, observable)
//        }
//    }
//
//    fun <T> observableIO2Main(fragment: RxFragment): ObservableTransformer<T, T> {
//        return { upstream ->
//            upstream.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).compose(fragment.bindToLifecycle<T>())
//        }
//    }
//
//    fun <T> flowableIO2Main(): FlowableTransformer<T, T> {
//        return { upstream ->
//            upstream
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//        }
//    }
//
//    private fun <T> composeContext(
//        context: Context,
//        observable: Observable<T>
//    ): ObservableSource<T> {
//        return when (context) {
//            is RxActivity -> observable.compose(context.bindUntilEvent(ActivityEvent.DESTROY))
//            is RxFragmentActivity -> observable.compose(context.bindUntilEvent(ActivityEvent.DESTROY))
//            is RxAppCompatActivity -> observable.compose(context.bindUntilEvent(ActivityEvent.DESTROY))
//            else -> observable
//        }
//    }
}