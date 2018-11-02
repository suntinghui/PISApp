package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.widgets.LabelTextView
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.setVisible
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

        mHeaderBar.setTitleText("应急反馈")

        queryDetail()
    }

    private fun queryDetail() {
        this.showLoading()
        mPresenter.getEmergencyInfoModel(intent.getStringExtra("ID"), PISUtil.getTokenKey())
    }

    private fun queryAtt(busId: String) {
        mPresenter.getAttList(busId, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerEmergencyInfoComponent.builder().activityComponent(mActivityComponent).emergencyInfoModule(EmergencyInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetDetailResult(result: EmergencyInfo) {
        this.hideLoading()

        queryAtt(result.EmInfoId)

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

    override fun onGetAttListResult(result: List<AttModel>) {
        this.hideLoading()

        if (result.isEmpty()) {
            mImagePicker.setVisible(false)
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("图片信息", "暂无"))
        } else {
            mImagePicker.setVisible(true)
            mImagePicker.setEditable(false)
                    .setAttType(BaseConstant.Att_Type_Driving)
                    .setNetImages(result)
        }
    }
}