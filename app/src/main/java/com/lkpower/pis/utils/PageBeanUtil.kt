package com.lkpower.pis.utils

import com.google.gson.Gson
import com.lkpower.base.common.BaseConstant
import com.lkpower.pis.data.protocol.PageBean

object PageBeanUtil {

    /*
    请求时可以不带TotalRecordSize 和 TotalPage
     */
    fun getPageBeanJson(currentPage: Int): String {
        var pageBean = PageBean(currentPage.toString(), BaseConstant.PageSize.toString(), "-1", "-1")
        return Gson().toJson(pageBean)
    }

}