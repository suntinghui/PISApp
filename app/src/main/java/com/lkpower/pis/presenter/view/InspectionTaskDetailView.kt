package com.lkpower.pis.presenter.view

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.MissionStateInfo
import com.lkpower.pis.data.protocol.XJ_CZSL

interface InspectionTaskDetailView : BaseView {

    fun onGetDetailResult(result: MissionStateInfo)
    fun onUpdateMissionInfo(result: Boolean)
    fun onGetAttListResult(result: List<AttModel>)
    
}