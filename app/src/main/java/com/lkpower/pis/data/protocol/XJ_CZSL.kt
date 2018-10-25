package com.lkpower.pis.data.protocol

/*
车站实例信息
 */
data class XJ_CZSL(
        val ID: String,
        val InstanceId: String,
        val stationId: String,
        val stationName: String,
        val stationType: String,
        val arrivalTime: String,
        val startTime: String,
        val arrivalDay: String,
        val remark: String,
        val updateUser: String,
        val updateTime: String,
        val longitude: String,
        val latitude: String,
        val prioritySet: String,
        val distance: String,
        val aheadTime: String,
        val orderNum: String,
        val goToBackType: String,
        val classId: String,
        val normalArriveDate: String,
        val State: String
)