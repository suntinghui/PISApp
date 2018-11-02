package com.lkpower.pis.presenter.view

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoutConfirmProj

interface SetoutConfirmProjListView : BaseView {

    fun onGetProjListResult(result:List<SetoutConfirmProj>)

    fun onConfirmResult(result:Boolean)

    fun onGetAttListResult(result: List<AttModel>)
}