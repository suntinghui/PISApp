package com.lkpower.pis.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import com.bigkoo.alertview.AlertView
import com.lkpower.pis.R
import com.orhanobut.logger.Logger
import es.dmoral.toasty.Toasty
import info.hoang8f.widget.FButton

object ViewUtils {

    // 简单提示框，只有一个确认按纽，点击后关闭提示框，没有其他动作
    fun showSimpleAlert(context: Context, msg: String) {
        Toasty.error(context, msg, Toast.LENGTH_LONG, true).show()
    }

    fun success(context: Context, msg: String) {
        Toasty.success(context, msg, Toast.LENGTH_SHORT, true).show()
    }

    fun error(context: Context, msg: String) {
        Toasty.error(context, msg, Toast.LENGTH_SHORT, true).show()
    }

    fun info(context: Context, msg: String) {
        Toasty.info(context, msg, Toast.LENGTH_SHORT, true).show()
    }

    fun warning(context: Context, msg: String) {
        Toasty.normal(context, msg).show()
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

    fun buttonEnable(context:Context, btn: FButton, enable: Boolean, colorEnable:Int = R.color.button_enable) {
        btn.isEnabled = enable

        if (enable) {
            btn.buttonColor = context.resources.getColor(colorEnable)
        } else {
            btn.buttonColor = context.resources.getColor(R.color.button_disable)
        }
    }


}