package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoutCheckIn

interface SetoutCheckinListView : BaseView {

    fun onGetListResult(result: List<SetoutCheckIn>)
}