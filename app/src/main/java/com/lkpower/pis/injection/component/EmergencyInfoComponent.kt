package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.AttachmentModule
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.injection.module.EmergencyInfoModule
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.ui.activity.EmergencyInfoAddActivity
import com.lkpower.pis.ui.activity.EmergencyInfoDetailActivity
import com.lkpower.pis.ui.activity.EmergencyInfoListActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(EmergencyInfoModule::class, AttachmentModule::class))

interface EmergencyInfoComponent {

    fun inject(activity: EmergencyInfoListActivity)
    fun inject(activity: EmergencyInfoDetailActivity)
    fun inject(activity: EmergencyInfoAddActivity)

}