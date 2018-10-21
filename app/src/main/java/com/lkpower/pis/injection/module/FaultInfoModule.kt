package com.lkpower.pis.injection.module

import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.FaultInfoService
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.impl.DrivingInfoServiceImpl
import com.lkpower.pis.service.impl.FaultInfoServiceImpl
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import dagger.Module
import dagger.Provides

@Module
class FaultInfoModule {

    @Provides
    fun provideFaultInfoService(faultInfoService: FaultInfoServiceImpl): FaultInfoService {
        return faultInfoService
    }
}