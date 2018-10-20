package com.lkpower.pis.data.protocol

/*
退乘任务
 */
data class SetOff(
        val SetOffInstanceId: String,
        val InstanceId: String,
        val SiteId: String,
        val SiteName: String,
        val SetOffId: String,
        val TaskStatus: String,
        val BeginTime: String,
        val OverConfirmTIme: String,
        val TaskObj: String,
        val ClassName: String,
        val SendTime: String
)