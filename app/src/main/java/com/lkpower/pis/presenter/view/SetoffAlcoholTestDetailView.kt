package com.lkpower.pis.presenter.view

import com.lkpower.base.data.protocol.AttModel
import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoffAlcoholTest
import com.lkpower.pis.data.protocol.SetoutAlcoholTest

interface SetoffAlcoholTestDetailView : BaseView {

    fun onGetDetailResult(result: SetoffAlcoholTest)

    fun setOffResult(result: Boolean)

    fun onGetAttListResult(result: List<AttModel>)
}