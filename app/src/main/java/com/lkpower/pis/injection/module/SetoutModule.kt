package com.lkpower.pis.injection.module

import com.lkpower.pis.service.SetoutService
import com.lkpower.pis.service.impl.SetoutServiceImpl
import dagger.Module
import dagger.Provides

@Module
class SetoutModule  {

    @Provides
    fun provideSetoutService(setoutService: SetoutServiceImpl): SetoutService {
        return setoutService
    }
}