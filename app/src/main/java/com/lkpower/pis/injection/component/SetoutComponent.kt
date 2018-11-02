package com.lkpower.pis.injection.component

import com.lkpower.pis.injection.PerComponentScope
import com.lkpower.pis.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.AttachmentModule
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.ui.activity.*
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(SetoutModule::class, AttachmentModule::class))
interface SetoutComponent {
    fun inject(activity: SetoutCheckinListActivity)
    fun inject(activity: SetoutCheckinDetailActivity)
    fun inject(activity: SetoutAlcoholTestListActivity)
    fun inject(activity: SetoutAlcoholTestDetailActivity)
    fun inject(activity: SetoutListActivity)
    fun inject(activity: SetoutDetailActivity)
    fun inject(activity: TaskConveyListActivity)
    fun inject(activity: TaskConveyDetailActivity)
    fun inject(activity: SetoutGroupTaskListActivity)
    fun inject(activity: SetoutConfirmProjListActivity)

}