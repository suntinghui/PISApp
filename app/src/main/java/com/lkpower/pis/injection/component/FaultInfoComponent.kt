package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.AttachmentModule
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.injection.module.FaultInfoModule
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.ui.activity.FaultHistoryConfirmActivity
import com.lkpower.pis.ui.activity.FaultHistoryListActivity
import com.lkpower.pis.ui.activity.FaultReportActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(FaultInfoModule::class, AttachmentModule::class))
interface FaultInfoComponent {

    fun inject(activity: FaultHistoryListActivity)
    fun inject(activity: FaultHistoryConfirmActivity)
    fun inject(activity: FaultReportActivity)


}