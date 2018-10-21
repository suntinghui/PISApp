package com.lkpower.pis.data.protocol

data class ListResult<T : Any>(val PageInfo: PageBean, val ListRecord: List<T>)