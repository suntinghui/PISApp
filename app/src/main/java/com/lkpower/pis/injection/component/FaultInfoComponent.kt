package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.injection.module.FaultInfoModule
import com.lkpower.pis.injection.module.LearnDocModule
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(FaultInfoModule::class))
interface FaultInfoComponent {

}