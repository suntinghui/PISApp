package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.lkpower.pis.common.AppManager
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.umeng.analytics.MobclickAgent
import com.umeng.message.PushAgent
import org.jetbrains.anko.find
import java.lang.Exception

/*
    Activity基类，业务无关
 */
open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppManager.instance.addActivity(this)

        PushAgent.getInstance(this).onAppStart()

        //ARouter注册
        ARouter.getInstance().inject(this)
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            Bus.unregister(this)
        } catch (e: Exception) {
        }


        AppManager.instance.finishActivity(this)
    }

    //获取Window中视图content
    val contentView: View
        get() {
            val content = find<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
        }
}
