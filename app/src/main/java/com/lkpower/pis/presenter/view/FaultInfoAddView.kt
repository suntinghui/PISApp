package com.lkpower.pis.presenter.view

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.*

interface FaultInfoAddView : BaseView {

    fun onAddDetailResult(result: CommonReturn)

    fun onFailPartResult(result: List<SysDic>)

    fun onFaultTypeResult(result: List<SysDic>)

    fun onCheckTypeResult(result: List<SysDic>)

}