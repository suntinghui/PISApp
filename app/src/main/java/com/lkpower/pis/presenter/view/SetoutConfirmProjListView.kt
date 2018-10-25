package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoutConfirmProj

interface SetoutConfirmProjListView : BaseView {

    fun onGetProjListResult(result:List<SetoutConfirmProj>)

    fun onConfirmResult(result:Boolean)
}