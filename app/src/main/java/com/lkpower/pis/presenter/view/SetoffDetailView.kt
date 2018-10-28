package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.SetoffInfo
import com.lkpower.pis.data.protocol.SetoutInfo

interface SetoffDetailView : BaseView {

    fun onGetDetailResult(result: SetoffInfo)

    fun setOffResult(result: CommonReturn)
}