package com.lkpower.pis.common

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.lkpower.pis.injection.component.AppComponent
import com.lkpower.pis.injection.component.DaggerAppComponent
import com.lkpower.pis.injection.module.AppModule
import com.lkpower.pis.ui.service.UMengNotificationService
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.*
import org.android.agoo.huawei.HuaWeiRegister

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

        registerUMPush()

        registerHuawei()

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
        mPushAgent.setNoDisturbMode(0, 0, 0, 0) // 关闭免打扰模式
        mPushAgent.resourcePackageName = "com.lkpower.pis"

        mPushAgent.notificationPlaySound = MsgConstant.NOTIFICATION_PLAY_SERVER //声音
        mPushAgent.notificationPlayLights = MsgConstant.NOTIFICATION_PLAY_SERVER//呼吸灯
        mPushAgent.notificationPlayVibrate = MsgConstant.NOTIFICATION_PLAY_SERVER //振动

        mPushAgent.register(object : IUmengRegisterCallback {
            // //注册成功会返回device token
            override fun onSuccess(p0: String?) {
                Logger.e("注册UMeng成功！Device_Token: ${p0}")
            }

            override fun onFailure(p0: String?, p1: String?) {
                Logger.e("注册UMeng失败！$p0  $p1")
            }
        })

        mPushAgent.setPushIntentServiceClass(UMengNotificationService::class.java)

    }

    private fun registerHuawei() {
        HuaWeiRegister.register(this)
    }

    // 全局伴生对象
    companion object {
        lateinit var context: Context
    }

}