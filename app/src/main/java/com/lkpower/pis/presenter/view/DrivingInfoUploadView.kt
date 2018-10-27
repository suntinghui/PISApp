package com.lkpower.pis.presenter.view

import com.lkpower.base.data.protocol.AttModel
import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.*

interface DrivingInfoUploadView : BaseView {

    fun onUploadDetailResult(result: CommonReturn)

}