package com.lkpower.pis.presenter.view

import com.lkpower.pis.presenter.view.BaseView
import com.lkpower.pis.data.protocol.*

interface EmergencyInfoAddView : BaseView {

    fun onUploadDetailResult(result: CommonReturn)

}