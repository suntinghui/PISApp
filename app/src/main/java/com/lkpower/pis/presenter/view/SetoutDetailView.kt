package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoutInfo

interface SetoutDetailView : BaseView {

    fun onGetDetailResult(result: SetoutInfo)

    fun setOutResult(result: String)
}