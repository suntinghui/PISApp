package com.lkpower.pis.data.protocol

/*
退乘报到任务
 */
data class SetoffCheckIn(
        val ID: String,
        val SetOfftInstance: String,
        val TaskStatus: String,
        val LatestCheckInTime: String,
        val DoneTime: String,
        val SiteName: String,
        val BeginTime: String,
        val TaskObj: String,
        val ClassName: String,
        val SendTime: String
)