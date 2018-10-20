package com.lkpower.pis.data.protocol

data class SetOutConfirmProj(
        val ID: String,
        val GroupTaskId: String,
        val GroupTask: String,
        val ProjectId: String,
        val ProjectName: String,
        val ConfirmTime: String,
        val DetailSetId: String,
        val TaskStatus: String,
        val DoneTime: String
)