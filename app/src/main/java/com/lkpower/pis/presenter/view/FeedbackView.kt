package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.CommonReturn

interface FeedbackView : BaseView {

    fun onAddResult(result: CommonReturn)


}