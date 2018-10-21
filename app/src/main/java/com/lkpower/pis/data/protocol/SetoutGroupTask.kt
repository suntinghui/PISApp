package com.lkpower.pis.data.protocol

// 出乘确认项目任务（班组出乘任务）
data class SetoutGroupTask(
        val ID: String,
        val InstanceId: String,
        val SiteId: String,
        val SiteName: String,
        val SetOffId: String,
        val TaskStatus: String,
        val DoneTime: String
)