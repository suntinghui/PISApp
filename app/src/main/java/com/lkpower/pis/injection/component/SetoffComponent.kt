package com.lkpower.pis.injection.component

import com.lkpower.pis.injection.PerComponentScope
import com.lkpower.pis.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.AttachmentModule
import com.lkpower.pis.injection.module.SetoffModule
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.ui.activity.*
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(SetoffModule::class, AttachmentModule::class))
interface SetoffComponent {
    fun inject(activity: SetoffCheckinListActivity)
    fun inject(activity: SetoffCheckinDetailActivity)
    fun inject(activity: SetoffAlcoholTestListActivity)
    fun inject(activity: SetoffAlcoholTestDetailActivity)
    fun inject(activity: SetoffListActivity)
    fun inject(activity: SetoffDetailActivity)

}