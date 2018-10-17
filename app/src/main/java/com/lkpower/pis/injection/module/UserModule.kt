package com.lkpower.pis.injection.module

import com.lkpower.pis.service.UserService
import com.lkpower.pis.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule  {

    @Provides
    fun provideUserService(userService: UserServiceImpl): UserService {
        return userService
    }
}