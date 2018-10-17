package com.lkpower.pis.injection.module

import com.lkpower.pis.service.TasktanceService
import com.lkpower.pis.service.UserService
import com.lkpower.pis.service.impl.TasktanceServiceImpl
import com.lkpower.pis.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

@Module
class TasktanceModule  {

    @Provides
    fun provideUserService(tasktanceService: TasktanceServiceImpl): TasktanceService {
        return tasktanceService
    }
}