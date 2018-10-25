package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.MissionStateInfo

interface InspectionTaskListView : BaseView {

    fun onGetListResult(result: List<MissionStateInfo>)
}