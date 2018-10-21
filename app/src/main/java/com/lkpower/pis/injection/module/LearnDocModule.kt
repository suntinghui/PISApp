package com.lkpower.pis.injection.module

import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import dagger.Module
import dagger.Provides

@Module
class LearnDocModule  {

    @Provides
    fun provideLearnService(learnService: LearnDocServiceImpl): LearnDocService {
        return learnService
    }
}