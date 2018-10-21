package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoutAlcoholTest

interface SetoutAlcoholTestListView : BaseView {

    fun onGetListResult(result: List<SetoutAlcoholTest>)
}