package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.AttachmentModule
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.injection.module.InspectionModule
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.ui.activity.InspectionStationListActivity
import com.lkpower.pis.ui.activity.InspectionTaskDetailActivity
import com.lkpower.pis.ui.activity.InspectionTaskListActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(InspectionModule::class, AttachmentModule::class))
interface InspectionComponent {

    fun inject(activity: InspectionStationListActivity)
    fun inject(activity: InspectionTaskListActivity)
    fun inject(activity: InspectionTaskDetailActivity)

}