package com.lkpower.pis.data.protocol

/*
出乘报道任务
 */
data class SetoutCheckIn(
        val ID: String,
        val SetOutInstanceId: String,
        val TaskStatus: String,
        val LatestCheckInTime: String,
        val SiteName: String,
        val BeginTime: String,
        val TaskObj: String,
        val ClassName: String,
        val SendTime: String

)