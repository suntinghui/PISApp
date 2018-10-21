package com.lkpower.pis.service

import io.reactivex.Observable

interface SettingService {

    fun addFeebback(feedbackInfo: String, tokenKey: String): Observable<String>

}