package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.*

interface EmergencyInfoListView : BaseView {

    fun onGetListResult(result: ListResult<EmergencyInfo>)
}