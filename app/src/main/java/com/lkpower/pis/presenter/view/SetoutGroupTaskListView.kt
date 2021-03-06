package com.lkpower.pis.presenter.view

import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.SetoutGroupTask

interface SetoutGroupTaskListView : BaseView {

    fun onGetListResult(result: List<SetoutGroupTask>)
}