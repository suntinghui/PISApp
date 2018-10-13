package com.lkpower.base.common

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.lkpower.base.injection.component.AppComponent
import com.lkpower.base.injection.component.DaggerAppComponent
import com.lkpower.base.injection.module.AppModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

open class BaseApplication : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initAppInjection()

        context = this

        Logger.addLogAdapter(AndroidLogAdapter())

        //ARouter初始化
        ARouter.openLog()    // 打印日志
        ARouter.openDebug()
        ARouter.init(this)

    }

    fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

    // 全局伴生对象
    companion object {
        lateinit var context : Context
    }

}