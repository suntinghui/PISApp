package com.lkpower.pis.data.protocol

data class FirAppInfo (
        val name:String,
        val version:String,
        val changelog:String,
        val versionShort:String,
        val build:String,
        val installUrl:String,
        val install_url:String,
        val update_url:String,

        val download_token:String
)