package com.lkpower.pis.injection.module

import com.lkpower.pis.service.AttachmentService
import com.lkpower.pis.service.DrivingInfoService
import com.lkpower.pis.service.LearnDocService
import com.lkpower.pis.service.impl.AttachmentServiceImpl
import com.lkpower.pis.service.impl.DrivingInfoServiceImpl
import com.lkpower.pis.service.impl.LearnDocServiceImpl
import dagger.Module
import dagger.Provides

@Module
class AttachmentModule {

    @Provides
    fun provideAttachmentService(attachmentService: AttachmentServiceImpl): AttachmentService {
        return attachmentService
    }
}