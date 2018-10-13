package com.lkpower.pis.presenter

import com.kotlin.base.rx.BaseSubscriber
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.execute
import com.lkpower.base.presenter.BasePresenter
import com.lkpower.pis.presenter.view.SettingView
import com.lkpower.pis.service.SettingService
import javax.inject.Inject

class SettingPresenter @Inject constructor() : BasePresenter<SettingView>() {

    @Inject
    lateinit var settingService:SettingService


}