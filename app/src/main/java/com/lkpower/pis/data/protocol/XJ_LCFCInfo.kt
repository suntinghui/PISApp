package com.lkpower.pis.data.protocol

/*
   发车实例
 */
data class XJ_LCFCInfo(
        val ID: String,
        val classId: String,
        val ClassName: String,
        val zbsId: String,
        val serialNumber: String,
        val SendTime: String,
        val overTime: String,
        val TrainmanId: String,
        val TrainmanName: String,
        val MemberRoleName: String

)