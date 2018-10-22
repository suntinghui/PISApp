package com.lkpower.pis.utils

import com.google.gson.Gson
import com.lkpower.base.common.BaseConstant
import com.lkpower.pis.data.protocol.PageBean

object PageBeanUtil {

    /*
    请求时可以不带TotalRecordSize 和 TotalPage
     */
    fun getPageBean(currentPage: Int): PageBean {
        return PageBean(currentPage.toString(), BaseConstant.PageSize.toString(), "", "")
    }

    fun getPageBeanJson(currentPage: Int): String {
        var pageBean = getPageBean(currentPage)
        return Gson().toJson(pageBean)
    }

}