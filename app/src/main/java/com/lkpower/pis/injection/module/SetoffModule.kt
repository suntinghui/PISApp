package com.lkpower.pis.injection.module

import com.lkpower.pis.service.SetoffService
import com.lkpower.pis.service.SetoutService
import com.lkpower.pis.service.impl.SetoffServiceImpl
import com.lkpower.pis.service.impl.SetoutServiceImpl
import dagger.Module
import dagger.Provides

@Module
class SetoffModule  {

    @Provides
    fun provideSetoffService(setoffService: SetoffServiceImpl): SetoffService {
        return setoffService
    }
}