package com.lkpower.base.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.view.inputmethod.InputMethodManager
import com.bigkoo.alertview.AlertView

object ViewUtils {

    // 简单提示框，只有一个确认按纽，点击后关闭提示框，没有其他动作
    fun showSimpleAlert(context: Context, msg: String) {
        AlertView("提示", msg, null, arrayOf("确定"), null, context, AlertView.Style.Alert, null).show();
    }

    // 关闭键盘
    fun closeKeyboard(context: Activity) {
        var imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive && context.currentFocus != null) {
            if (context.currentFocus.windowToken != null) {
                imm.hideSoftInputFromWindow(context.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }


}