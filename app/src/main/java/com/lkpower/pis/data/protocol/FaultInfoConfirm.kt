package com.lkpower.pis.data.protocol

data class FaultInfoConfirm(
        val ID: String,
        val FaultId: String,
        val Result: String,
        val PersonId: String,
        val PersonName: String,
        val Remark: String,
        val ConfirmTime: String

)