package com.lkpower.pis.injection.module

import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.InspectionService
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.impl.DrivingInfoServiceImpl
import com.lkpower.pis.service.impl.InspectionServiceImpl
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import dagger.Module
import dagger.Provides

@Module
class InspectionModule {

    @Provides
    fun provideInspectionService(inspectionService: InspectionServiceImpl): InspectionService {
        return inspectionService
    }
}