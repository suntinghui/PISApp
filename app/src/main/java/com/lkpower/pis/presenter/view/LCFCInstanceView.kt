package com.lkpower.pis.presenter.view

import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.XJ_LCFC

// 发车实例

interface LCFCInstanceView:BaseView {
    fun onGetLCFCInstanceResult(result:List<XJ_LCFC>)
}