package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.XJ_CZSL

interface InspectionStationListView : BaseView {

    fun onGetListResult(result: List<XJ_CZSL>)
}