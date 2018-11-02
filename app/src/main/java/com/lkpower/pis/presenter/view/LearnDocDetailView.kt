package com.lkpower.pis.presenter.view

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.LearnDoc

interface LearnDocDetailView : BaseView {

    fun onGetDetailResult(result: LearnDoc)

    fun setReadResult(result: Boolean)

    fun onGetAttListResult(result: List<AttModel>)
}