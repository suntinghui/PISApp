package com.lkpower.pis.presenter.view

import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.*

interface DrivingInfoUploadView : BaseView {

    fun onUploadDetailResult(result: CommonReturn)

}