package com.lkpower.pis.data.protocol

data class SysDic(
        val ID: String,
        val DicValue: String,
        val DicTypeCode: String,
        val ParentId: String,
        val DicOrder: String,
        val RelParentId: String
)