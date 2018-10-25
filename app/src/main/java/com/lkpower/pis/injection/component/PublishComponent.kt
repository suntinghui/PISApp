package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.injection.module.PublishModule
import com.lkpower.pis.ui.activity.PublishDetailActivity
import com.lkpower.pis.ui.activity.PublishListActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(PublishModule::class))
interface PublishComponent {
    fun inject(activity: PublishListActivity)
    fun inject(activity: PublishDetailActivity)

}