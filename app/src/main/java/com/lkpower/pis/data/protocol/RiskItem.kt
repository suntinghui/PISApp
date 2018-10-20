package com.lkpower.pis.data.protocol

/*
计划任务相关风险项
 */
data class RiskItem(
        val ItemId: String,
        val TaskDetailId: String,
        val RiskDic: String,
        val RiskDicName: String,
        val NeedFeedBack: String,
        val RefItemId: String,
        val FeedBackContent: String,
        val FeedBackTime: String,
        val FeedBackPerson: String
)