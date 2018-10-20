package com.lkpower.pis.data.protocol

// 计划任务传达记录
data class TaskConveyDetail(
        val ConveyDetailId: String,
        val ClassId: String,
        val TaskTitle: String,
        val TaskContent: String,
        val SetOutType: String,
        val SetOutTypeName: String,
        val CompleteTime: String,
        val Publisher: String,
        val PublisherName: String,
        val PublishDate: String,
        val RiskItems: List<RiskItem>,
        val ConfirmDetail: TaskConfirmDetail
)