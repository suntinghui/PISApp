package com.lkpower.pis.service

import com.lkpower.pis.data.protocol.CommonReturn
import io.reactivex.Observable

interface SettingService {

    fun addFeebback(feedbackInfo: String, tokenKey: String): Observable<CommonReturn>

}