package com.lkpower.pis.presenter.view

import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoffAlcoholTest
import com.lkpower.pis.data.protocol.SetoutAlcoholTest

interface SetoffAlcoholTestListView : BaseView {

    fun onGetListResult(result: List<SetoffAlcoholTest>)
}