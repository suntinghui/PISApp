package com.lkpower.pis.data.protocol

data class SetoutAlcoholTestInfo(
        val ID: String,
        val SetOutInstanceId: String,
        val TaskStatus: String,
        val LatesttAlcoholTestTime: String,
        val DoneTime: String,
        val Result: String,
        val SiteName: String,
        val BeginTime: String,
        val TaskObj: String,
        val ClassName: String,
        val SendTime: String
)