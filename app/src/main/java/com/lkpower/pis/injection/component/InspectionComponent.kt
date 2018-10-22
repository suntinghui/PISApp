package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.injection.module.InspectionModule
import com.lkpower.pis.injection.module.LearnDocModule
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(InspectionModule::class))
interface InspectionComponent {

}