package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.TasktanceModule
import com.lkpower.pis.ui.activity.*
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(TasktanceModule::class))
interface TasktanceComponent {
    fun inject(activity: SetoutCheckinListActivity)
    fun inject(activity: SetoutCheckinDetailActivity)
    fun inject(activity: SetoutAlcoholTestListActivity)
    fun inject(activity: SetoutAlcoholTestDetailActivity)
    fun inject(activity: SetoutListActivity)
    fun inject(activity:SetoutDetailActivity)
}