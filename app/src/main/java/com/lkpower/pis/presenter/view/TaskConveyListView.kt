package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.TaskConveyDetail

interface TaskConveyListView : BaseView {

    fun onGetListResult(result:List<TaskConveyDetail>)
}