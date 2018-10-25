package com.kotlin.base.rx

import com.lkpower.base.presenter.view.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.net.SocketTimeoutException

/*
    Rx订阅者默认实现
 */
open class BaseSubscriber<T>(val baseView: BaseView) : Observer<T> {

    override fun onSubscribe(p0: Disposable) {
    }


    override fun onComplete() {
        baseView.hideLoading()
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()

        if (e is BaseException) {
            baseView.onError(e.Mesg)
        } else if (e is DataNullException){
            baseView.onDataIsNull()
        } else if (e is SocketTimeoutException) {
            baseView.onError("网络连接超时，请检查网络或稍候重试")
        } else {
            baseView.onError(e.toString())
        }

    }
}
