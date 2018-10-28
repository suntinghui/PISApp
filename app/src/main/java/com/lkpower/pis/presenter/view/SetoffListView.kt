package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoffInfo
import com.lkpower.pis.data.protocol.SetoutInfo

interface SetoffListView : BaseView {

    fun onGetListResult(result: List<SetoffInfo>)
}