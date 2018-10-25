package com.lkpower.pis.utils

object PISUtil {

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