package com.lkpower.pis.injection.component

import com.lkpower.base.injection.PerComponentScope
import com.lkpower.base.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.SettingModule
import com.lkpower.pis.ui.activity.FeedbackActivity
import com.lkpower.pis.ui.activity.SettingActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(SettingModule::class))

interface SettingComponent {
    fun inject(activity: FeedbackActivity)
}