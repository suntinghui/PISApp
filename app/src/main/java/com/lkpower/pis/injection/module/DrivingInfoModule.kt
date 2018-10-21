package com.lkpower.pis.injection.module

import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.impl.DrivingInfoServiceImpl
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import dagger.Module
import dagger.Provides

@Module
class DrivingInfoModule {

    @Provides
    fun provideDrivingInfoService(drivingInfoService: DrivingInfoServiceImpl): DrivingInfoService {
        return drivingInfoService
    }
}