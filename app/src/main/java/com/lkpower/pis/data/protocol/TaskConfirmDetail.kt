package com.lkpower.pis.data.protocol

/*
计划任务传达确认
 */
data class TaskConfirmDetail(
        val ID: String,
        val ConveyDetailId: String,
        val ConfirmPerson: String,
        val ConfirmTime: String,
        val FeedBack: String
)