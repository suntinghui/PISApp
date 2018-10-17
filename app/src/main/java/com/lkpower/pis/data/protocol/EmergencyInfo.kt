package com.lkpower.pis.data.protocol

data class EmergencyInfo(
        val EmInfoId: String,
        val InstanceId: String,
        val Instance: LCFCInfo,
        val FeedBackUser: String,
        val FeedBackUserName: String,
        val FeedBackTime: String,
        val FeedBackContent: String,
        val PhotoInfoList: List<AttModel>

)