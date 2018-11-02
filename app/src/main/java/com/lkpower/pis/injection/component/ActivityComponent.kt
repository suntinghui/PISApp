package com.lkpower.pis.injection.component

import android.app.Activity
import android.content.Context
import com.lkpower.pis.injection.ActivityScope
import com.lkpower.pis.injection.module.ActivityModule
import com.lkpower.pis.injection.module.LifecycleProviderModule
import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Component

/*
    Activity级别Component
 */
@ActivityScope
@Component(dependencies = [(AppComponent::class)],modules = [(ActivityModule::class), (LifecycleProviderModule::class)])
interface ActivityComponent {

    fun activity():Activity
    fun context(): Context
    fun lifecycleProvider(): LifecycleProvider<*>
}