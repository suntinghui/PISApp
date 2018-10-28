package com.lkpower.pis.presenter.view

import com.lkpower.base.data.protocol.AttModel
import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.*

interface FaultInfoAddView : BaseView {

    fun onAddDetailResult(result: CommonReturn)

    fun onFailPartResult(result:List<SysDic>)

    fun onFaultTypeResult(result:List<SysDic>)

}