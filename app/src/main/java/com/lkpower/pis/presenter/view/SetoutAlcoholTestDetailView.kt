package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoutAlcoholTest

interface SetoutAlcoholTestDetailView : BaseView {

    fun onGetDetailResult(result: SetoutAlcoholTest)

    fun setOutResult(result: String)
}