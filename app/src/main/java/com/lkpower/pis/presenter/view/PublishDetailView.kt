package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.MissionStateInfo
import com.lkpower.pis.data.protocol.PublishInfo
import com.lkpower.pis.data.protocol.XJ_CZSL

interface PublishDetailView : BaseView {

    fun onGetDetailResult(result: PublishInfo)
    
}