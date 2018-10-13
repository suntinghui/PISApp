package com.lkpower.pis.injection.module

import com.lkpower.pis.service.SettingService
import com.lkpower.pis.service.impl.SettingServiceImpl
import dagger.Module
import dagger.Provides

@Module
class SettingModule {

    @Provides
    fun provideSettingService(settingService:SettingServiceImpl):SettingService {
        return settingService
    }
}