package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.TasktanceModule
import com.lkpower.pis.ui.activity.OutCheckinListActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(TasktanceModule::class))
interface TasktanceComponent {
    fun inject(activity: OutCheckinListActivity)
}