package com.lkpower.pis.injection.module

import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/*
    Rx生命周期管理能用Module
 */

@Module
class LifecycleProviderModule(private val lifecycleProvider:LifecycleProvider<*>) {

    @Provides
    fun provideLifecycleProvider():LifecycleProvider<*> {
        return this.lifecycleProvider
    }

}