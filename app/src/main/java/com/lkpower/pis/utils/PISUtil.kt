package com.lkpower.pis.utils

import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import java.util.*

object PISUtil {

    fun setTokenKey(tokenKey: String) {
        AppPrefsUtils.putString(BaseConstant.kTokenKey, tokenKey)
    }

    fun getTokenKey(): String {
        return AppPrefsUtils.getString(BaseConstant.kTokenKey)
    }

    fun setInstanceId(instanceId: String) {
        return AppPrefsUtils.putString(BaseConstant.kInstanceId, instanceId)
    }

    fun getInstanceId(): String {
        return AppPrefsUtils.getString(BaseConstant.kInstanceId)
    }

    fun getUUID(): String {
        return UUID.randomUUID().toString()
    }

    fun getInspectionStationState(code: String): String {
        return when (code.toInt()) {
            0 -> "未完成"

            1 -> "已完成"

            -1 -> "无任务"

            else -> "未知"

        }
    }

    /*
    完成状态：
    1:新任务
    2：待执行
    3：执行中
    4：已完成
     */
    fun getInspectionTaskState(code: String): String {
        return when (code.toInt()) {
            1 -> "新任务"

            2 -> "待执行"

            3 -> "执行中"

            4 -> "已完成"

            else -> "未知"

        }
    }
}