package com.lkpower.pis.injection.component

import com.lkpower.pis.injection.PerComponentScope
import com.lkpower.pis.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.AttachmentModule
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.ui.activity.DrivingInfoDetailActivity
import com.lkpower.pis.ui.activity.DrivingInfoListActivity
import com.lkpower.pis.ui.activity.DrivingInfoUploadActivity
import com.lkpower.pis.ui.activity.PreviewImageActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(AttachmentModule::class))
interface AttachmentComponent {
    fun inject(activity: PreviewImageActivity)

}