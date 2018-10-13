package com.lkpower.pis.data.protocol

data class Category(
        val id: Int,
        val title: String,
        val icon: Int,
        val parentId: Int,
        val action: String
)