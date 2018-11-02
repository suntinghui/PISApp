package com.lkpower.pis.presenter.view

import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.SetoutCheckIn

interface LearnDocListView : BaseView {

    fun onGetListResult(result: ListResult<LearnDoc>)
}