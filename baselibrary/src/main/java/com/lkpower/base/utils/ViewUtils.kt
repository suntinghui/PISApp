package com.lkpower.base.utils

import android.content.Context
import com.bigkoo.alertview.AlertView

object ViewUtils {

    // 简单提示框，只有一个确认按纽，点击后关闭提示框，没有其他动作
    fun showSimpleAlert(context: Context, msg:String) {
        AlertView("提示", msg, null, arrayOf("确定"), null, context, AlertView.Style.Alert, null).show();
    }


}