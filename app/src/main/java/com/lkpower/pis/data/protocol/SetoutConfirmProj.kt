package com.lkpower.pis.data.protocol

/*
出乘确认项目任务具体(明细)项目任务
 */
data class SetoutConfirmProj(
        val ID: String,
        val GroupTaskId: String,
        val GroupTask: SetoutGroupTask,
        val ProjectId: String,
        val ProjectName: String,
        val ConfirmTime: String,
        val ConfirmRemark: String,
        val DetailSetId: String,
        val TaskStatus: String,
        val DoneTime: String
)