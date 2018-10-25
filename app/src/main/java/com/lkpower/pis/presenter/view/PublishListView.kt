package com.lkpower.pis.presenter.view

import com.lkpower.base.presenter.view.BaseView
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.PublishInfo

interface PublishListView : BaseView {

    fun onGetListResult(result: ListResult<PublishInfo>)
}