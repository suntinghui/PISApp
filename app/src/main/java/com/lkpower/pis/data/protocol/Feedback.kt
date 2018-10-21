package com.lkpower.pis.data.protocol

data class Feedback(
        val ID: String,
        val CarNumberId: String,
        val CarNumberName: String,
        val GroupId: String,
        val GroupName: String,
        val SubmitTime: String,
        val DeviceInfo: String,
        val Remark: String,
        val InstanceId: String,
        val FeedBackUser: String,
        val FeedBackUserName: String
)