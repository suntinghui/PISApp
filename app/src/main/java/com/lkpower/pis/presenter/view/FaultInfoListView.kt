package com.lkpower.pis.presenter.view

import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.*

interface FaultInfoListView : BaseView {

    fun onGetListResult(result: ListResult<FaultInfo>)

    fun onFailPartResult(result:List<SysDic>)

    fun onFaultTypeResult(result:List<SysDic>)
}