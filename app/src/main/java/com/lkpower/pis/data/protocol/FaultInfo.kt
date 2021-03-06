package com.lkpower.pis.data.protocol

data class FaultInfo(

        val FaultId: String,
        val TrainNo: String,
        val PartId: String,
        val PartName: String,
        val FaultType: String,
        val FaultTypeName: String,
        val Remark: String,
        val CheckType: String,
        val CheckTypeName: String,
        val ReportUser: String,
        val ReportTime: String,
        val HandleResult: String,
        val InstanceId: String,
        var ConfirmInfo: FaultInfoConfirm

)