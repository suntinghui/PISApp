package com.kotlin.base.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.eightbitlab.rxbus.Bus
import com.lkpower.base.common.AppManager
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.find
import java.lang.Exception

/*
    Activity基类，业务无关
 */
open class BaseActivity: RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppManager.instance.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            Bus.unregister(this)
        } catch (e:Exception){}


        AppManager.instance.finishActivity(this)
    }

    //获取Window中视图content
    val contentView:View
        get() {
            val content = find<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
        }
}
