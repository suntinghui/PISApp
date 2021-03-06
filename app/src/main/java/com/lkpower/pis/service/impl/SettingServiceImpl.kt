package com.lkpower.pis.service.impl

import com.lkpower.pis.ext.convert
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.respository.SettingRespository
import com.lkpower.pis.service.SettingService
import io.reactivex.Observable
import javax.inject.Inject

class SettingServiceImpl @Inject constructor() : SettingService {

    @Inject
    lateinit var settingRespository: SettingRespository

    override fun addFeebback(feedbackInfo: String, tokenKey: String): Observable<CommonReturn> {
        return settingRespository.addFeedback(feedbackInfo, tokenKey).convert()
    }


}