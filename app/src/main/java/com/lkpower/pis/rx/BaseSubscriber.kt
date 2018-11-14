package com.lkpower.pis.rx

import com.lkpower.pis.presenter.view.BaseView
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
            var msg = if (e.Mesg.isNullOrEmpty()) "数据异常，请重试" else e.Mesg
            baseView.onError(msg)
        } else if (e is DataNullException) {
            baseView.onDataIsNull()
        } else if (e is SocketTimeoutException) {
            baseView.onError("网络连接超时，请检查网络或稍候重试")
        } else {
            e.printStackTrace()
            baseView.onError("数据异常，请稍候重试")
        }

    }
}
