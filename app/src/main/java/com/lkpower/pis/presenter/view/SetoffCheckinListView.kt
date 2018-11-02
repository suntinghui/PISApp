package com.lkpower.pis.presenter.view

import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoffCheckIn
import com.lkpower.pis.data.protocol.SetoutCheckIn

interface SetoffCheckinListView : BaseView {

    fun onGetListResult(result: List<SetoffCheckIn>)
}