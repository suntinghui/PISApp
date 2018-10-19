package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoutAlcoholTestInfo
import com.lkpower.pis.data.protocol.SetoutCheckInInfo

interface SetoutAlcoholTestListView : BaseView {

    fun onGetListResult(result: List<SetoutAlcoholTestInfo>)
}