package com.lkpower.pis.data.protocol

data class PublishInfo(
        val ID: String,
        val Title: String,
        val Content: String,
        val DutyUser: String,
        val SubmitTime: String,
        val StateFlag: String,
        val IsRead: String
)