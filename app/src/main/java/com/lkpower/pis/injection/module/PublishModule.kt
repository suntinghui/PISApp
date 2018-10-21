package com.lkpower.pis.injection.module

import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.PublishService
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import com.lkpower.pis.service.impl.PublishServiceImpl
import dagger.Module
import dagger.Provides

@Module
class PublishModule {

    @Provides
    fun providePublishService(publishService: PublishServiceImpl): PublishService {
        return publishService
    }
}