package com.lkpower.pis.ui.activity

import com.kotlin.base.ui.activity.BaseMvpActivity
import com.lkpower.pis.data.protocol.OutCheckInInfo
import com.lkpower.pis.presenter.GetOutCheckinDetailPresenter
import com.lkpower.pis.presenter.view.OutCheckinDetailView

class OutCheckinDetailActivity:BaseMvpActivity<GetOutCheckinDetailPresenter>(), OutCheckinDetailView {
    override fun injectComponent() {
    }

    override fun onGetDetailResult(result: OutCheckInInfo) {
    }
}