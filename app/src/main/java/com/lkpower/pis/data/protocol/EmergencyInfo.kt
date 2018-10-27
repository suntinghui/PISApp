package com.lkpower.pis.data.protocol

data class EmergencyInfo(
        val EmInfoId: String,
        val InstanceId: String,
        val FeedBackUser: String,
        val FeedBackUserName: String,
        val FeedBackTime: String,
        val FeedBackContent: String,
        val DeviceId: String,
        val CarNumberName: String,
        val GroupName: String,
        val SendTime: String

)