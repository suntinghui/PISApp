package com.lkpower.pis.presenter.view

import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoffCheckIn
import com.lkpower.pis.data.protocol.SetoutCheckIn

interface SetoffCheckinDetailView : BaseView {

    fun onGetDetailResult(result: SetoffCheckIn)

    fun setOutResult(result: Boolean)
}