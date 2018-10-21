package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.SetoffModule
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.ui.activity.*
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(SetoffModule::class))
interface SetoffComponent {

}