package com.kotlin.base.rx

import com.lkpower.base.common.ResultCode
import com.lkpower.base.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    通用数据类型转换封装
 */
class BaseFunc<T> : Function<BaseResp<T>, Observable<T>> {
    override fun apply(t: BaseResp<T>): Observable<T> {
        if (t.Result != ResultCode.TRUE) {
            return Observable.error(BaseException(t.Mesg))
        }

        if (t.Data == null){
            return Observable.error(DataNullException())
        }
        return Observable.just(t.Data)
    }
}
