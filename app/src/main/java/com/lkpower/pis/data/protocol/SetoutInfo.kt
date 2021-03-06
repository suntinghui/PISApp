package com.lkpower.pis.data.protocol

/*
出乘任务实例
 */
data class SetoutInfo(
        val SetOutInstanceId: String,
        val InstanceId: String,
        val SiteId: String,
        val SiteName: String,
        val SetOffId: String,
        val TaskStatus: String,
        val BeginTime: String,
        val OverConfirmTIme: String,
        val TaskObj: String,
        val ClassName: String,
        val SendTime: String,
        val TaskPlaceSource: List<String>,
        val TaskPlace: String
)