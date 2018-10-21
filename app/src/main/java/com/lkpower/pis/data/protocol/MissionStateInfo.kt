package com.lkpower.pis.data.protocol

/*
巡检任务情况
 */
data class MissionStateInfo(
        val ID: String,
        val serialNumber: String,
        val missionId: String,
        val MissionNam: String,
        val MissionRemark: String,
        val executor: String,
        val state: String,
        val remark: String,
        val updateUser: String,
        val updateTime: String,
        val checkRead: String,
        val trainNumberId: String,
        val zbsId: String,
        val InstanceId: String,
        val PhotoInfoList: List<AttModel>
)