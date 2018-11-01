package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.lkpower.base.utils.PISUtil
import com.kotlin.base.widgets.LabelTextView
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.data.protocol.AttModel
import com.lkpower.base.ext.setVisible
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

        queryDetail()
    }

    private fun queryDetail() {
        this.showLoading()
        mPresenter.getDrivingInfoModel(intent.getStringExtra("ID"), PISUtil.getTokenKey())
    }

    private fun queryAtt(busId: String) {
        mPresenter.getAttList(busId, BaseConstant.Att_Type_Driving, PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerDrivingInfoComponent.builder().activityComponent(mActivityComponent).drivingInfoModule(DrivingInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetDetailResult(result: DrivingInfo) {
        this.hideLoading()

        queryAtt(result.ID)

        try {
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("车次名称", result.CarNumberName))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("组别名称", result.GroupName))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("提交时间", result.SubmitTime))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("信息描述", result.Remark))

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