package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.lkpower.pis.utils.PISUtil
import com.kotlin.base.widgets.LabelTextView
import com.lkpower.base.common.BaseConstant
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.EmergencyInfo
import com.lkpower.pis.injection.component.DaggerEmergencyInfoComponent
import com.lkpower.pis.injection.module.EmergencyInfoModule
import com.lkpower.pis.presenter.EmergencyInfoDetailPresenter
import com.lkpower.pis.presenter.view.EmergencyInfoDetailView
import kotlinx.android.synthetic.main.activity_driving_info_detail.*

class EmergencyInfoDetailActivity : BaseMvpActivity<EmergencyInfoDetailPresenter>(), EmergencyInfoDetailView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_driving_info_detail)

        loadData()
    }

    private fun loadData() {
        this.showLoading()
        mPresenter.getEmergencyInfoModel(intent.getStringExtra("ID"), PISUtil.getTokenKey())
    }

    override fun onDataIsNull() {
        this.hideLoading()
    }

    override fun injectComponent() {
        DaggerEmergencyInfoComponent.builder().activityComponent(mActivityComponent).emergencyInfoModule(EmergencyInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetDetailResult(result: EmergencyInfo) {
        this.hideLoading()

        try {
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("车次名称", result.CarNumberName))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("组别名称", result.GroupName))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("反馈内容", result.FeedBackContent))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("反馈人", result.FeedBackUserName))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("反馈时间", result.FeedBackTime))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}