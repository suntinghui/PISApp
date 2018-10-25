package com.lkpower.pis.injection.module

import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.impl.AttachmentServiceImpl
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import dagger.Module
import dagger.Provides

@Module
class LearnDocModule {

    @Provides
    fun provideLearnService(learnService: LearnDocServiceImpl): LearnDocService {
        return learnService
    }

    @Provides
    fun provideAttachmentService(attachmentService: AttachmentServiceImpl): AttachmentService {
        return attachmentService
    }
}