package com.lkpower.pis.injection.component

import android.app.Activity
import com.lkpower.pis.injection.PerComponentScope
import com.lkpower.pis.injection.component.ActivityComponent
import com.lkpower.pis.injection.module.UserModule
import com.lkpower.pis.ui.activity.LoginActivity
import com.lkpower.pis.ui.activity.PrimaryCategoryActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(UserModule::class))
interface UserComponent {
    fun inject(activity: LoginActivity)
    fun inject(activity: PrimaryCategoryActivity)
}