package com.lkpower.pis.data.protocol

data class DrivingInfo(
        val ID: String,
        val CarNumberId: String,
        val CarNumberName: String,
        val GroupId: String,
        val GroupName: String,
        val SubmitTime: String,
        val Remark: String,
        val StateFlag: String,
        val ReadFlag: String,
        val Publisher: String,
        val PublisherName: String,
        val DeviceId: String

)