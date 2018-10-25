package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.widgets.LabelTextView
import com.lkpower.base.common.BaseConstant
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.injection.component.DaggerDrivingInfoComponent
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.presenter.DrivingInfoDetailPresenter
import com.lkpower.pis.presenter.view.DrivingInfoDetailView
import kotlinx.android.synthetic.main.activity_driving_info_detail.*
import java.lang.Exception

class DrivingInfoDetailActivity : BaseMvpActivity<DrivingInfoDetailPresenter>(), DrivingInfoDetailView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_driving_info_detail)

        loadData()
    }

    private fun loadData() {
        this.showLoading()
        mPresenter.getDrivingInfoModel(intent.getStringExtra("ID"), AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }

    override fun onDataIsNull() {
        this.hideLoading()
    }

    override fun injectComponent() {
        DaggerDrivingInfoComponent.builder().activityComponent(mActivityComponent).drivingInfoModule(DrivingInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetDetailResult(result: DrivingInfo) {
        this.hideLoading()

        try {
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("车次名称", result.CarNumberName))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("组别名称", result.GroupName))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("提交时间", result.SubmitTime))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("信息描述", result.Remark))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}