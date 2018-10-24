package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoutCheckIn

interface SetoutCheckinDetailView : BaseView {

    fun onGetDetailResult(result: SetoutCheckIn)

    fun setOutResult(result: Boolean)
}