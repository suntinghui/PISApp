package com.lkpower.pis.injection.component

import com.lkpower.pis.injection.PerComponentScope
import com.lkpower.pis.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.ui.activity.LearnDocDetailActivity
import com.lkpower.pis.ui.activity.LearnDocListActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class), modules = arrayOf(LearnDocModule::class))
interface LearnDocComponent {

    fun inject(activity: LearnDocListActivity)
    fun inject(activity: LearnDocDetailActivity)

}