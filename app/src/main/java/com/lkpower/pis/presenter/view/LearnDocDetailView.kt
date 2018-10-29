package com.lkpower.pis.presenter.view

import com.lkpower.base.data.protocol.AttModel
import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.LearnDoc

interface LearnDocDetailView : BaseView {

    fun onGetDetailResult(result: LearnDoc)

    fun setReadResult(result: Boolean)

    fun onGetAttListResult(result: List<AttModel>)
}