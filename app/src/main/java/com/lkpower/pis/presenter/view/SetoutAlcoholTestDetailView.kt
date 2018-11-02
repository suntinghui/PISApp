package com.lkpower.pis.presenter.view

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoutAlcoholTest

interface SetoutAlcoholTestDetailView : BaseView {

    fun onGetDetailResult(result: SetoutAlcoholTest)

    fun setOutResult(result: Boolean)

    fun onGetAttListResult(result: List<AttModel>)
}