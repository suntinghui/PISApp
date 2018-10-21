package com.lkpower.pis.data.protocol

/*
退乘酒测任务
 */
data class SetoffAlcoholTest(
        val ID: String,
        val SetOfftInstanceId: String,
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