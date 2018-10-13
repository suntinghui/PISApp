package com.lkpower.pis.injection.module

import com.lkpower.pis.service.UserService
import com.lkpower.pis.service.impl.UserServiceImpl
import dagger.Module

@Module
class UserModule  {

    fun provideUserService(userService: UserServiceImpl): UserService {
        return userService
    }
}