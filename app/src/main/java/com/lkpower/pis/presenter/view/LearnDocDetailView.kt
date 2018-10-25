package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.SetoutCheckIn

interface LearnDocDetailView : BaseView {

    fun onGetDetailResult(result: LearnDoc)

    fun setOutResult(result: String)

    fun onGetAttListResult(result: List<AttModel>)
}