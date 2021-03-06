package com.lkpower.pis.rx

import com.lkpower.pis.common.ResultCode
import com.lkpower.pis.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    通用数据类型转换封装
 */
class BaseFunc<T> : Function<BaseResp<T>, Observable<T>> {
    override fun apply(t: BaseResp<T>): Observable<T> {
        if (t.Result == ResultCode.TRUE) {
            return Observable.just(t.Data)
        }

        return Observable.error(BaseException(t.Mesg))
    }
}
