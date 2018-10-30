package com.lkpower.base.common

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.lkpower.base.injection.component.AppComponent
import com.lkpower.base.injection.component.DaggerAppComponent
import com.lkpower.base.injection.module.AppModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.*

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

        initUMeng()

    }

    fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

    fun initUMeng() {
        UMConfigure.init(this, BaseConstant.UMENG_APPKEY, "FIR", UMConfigure.DEVICE_TYPE_PHONE, BaseConstant.UMENG_MESSAGE_SECRET)
    }

    fun registerUMPush() {
        var mPushAgent = PushAgent.getInstance(this)
        mPushAgent.displayNotificationNumber = 0 // 参数number可以设置为0~10之间任意整数。当参数为0时，表示不合并通知。
        mPushAgent.resourcePackageName = "com.lkpower.pis"

        mPushAgent.notificationPlaySound = MsgConstant.NOTIFICATION_PLAY_SERVER //声音
        mPushAgent.notificationPlayLights = MsgConstant.NOTIFICATION_PLAY_SERVER//呼吸灯
        mPushAgent.notificationPlayVibrate = MsgConstant.NOTIFICATION_PLAY_SERVER //振动

        mPushAgent.register(object : IUmengRegisterCallback {
            // //注册成功会返回device token
            override fun onSuccess(p0: String?) {
            }

            override fun onFailure(p0: String?, p1: String?) {
            }
        })


    }

    // 全局伴生对象
    companion object {
        lateinit var context: Context
    }

}