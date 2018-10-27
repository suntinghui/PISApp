package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.AttachmentModule
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.ui.activity.DrivingInfoDetailActivity
import com.lkpower.pis.ui.activity.DrivingInfoListActivity
import com.lkpower.pis.ui.activity.DrivingInfoUploadActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(DrivingInfoModule::class, AttachmentModule::class))
interface DrivingInfoComponent {
    fun inject(activity: DrivingInfoListActivity)
    fun inject(activity: DrivingInfoDetailActivity)
    fun inject(activity: DrivingInfoUploadActivity)

}