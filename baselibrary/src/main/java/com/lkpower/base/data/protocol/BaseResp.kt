package com.lkpower.base.data.protocol

/*
    能用响应对象
    @status:响应状态码
    @message:响应文字消息
    @data:具体响应业务对象
 */
data class BaseResp<out T>(val Result: Boolean, val Code: String, val Mesg: String, val Data: T)