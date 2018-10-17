package com.kotlin.base.rx

import com.lkpower.base.common.ResultCode
import com.lkpower.base.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    Boolean类型转换封装
 */
class BaseFuncBoolean<T>: Function<BaseResp<T>, Observable<Boolean>> {
    override fun apply(t: BaseResp<T>): Observable<Boolean> {
        if (t.Result != ResultCode.TRUE){
            return Observable.error(BaseException(t.Mesg))
        }

        return Observable.just(true)
    }
}
