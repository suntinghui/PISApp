package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.injection.module.PublishModule
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(PublishModule::class))
interface PublishComponent {

}