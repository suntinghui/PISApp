package com.lkpower.pis.utils

object PISUtil {

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