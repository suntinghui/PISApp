package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.widgets.ImagePickerView
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.injection.component.DaggerDrivingInfoComponent
import com.lkpower.pis.injection.module.DrivingInfoModule
import com.lkpower.pis.presenter.DrivingInfoUploadPresenter
import com.lkpower.pis.presenter.view.DrivingInfoUploadView
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils.buttonEnable
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_driving_info_upload.*
import org.jetbrains.anko.toast

@Route(path = "/pis/DrivingInfoUploadActivity")
class DrivingInfoUploadActivity : BaseMvpActivity<DrivingInfoUploadPresenter>(), DrivingInfoUploadView {

    private lateinit var uuid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_driving_info_upload)

        uuid = PISUtil.getUUID()

        initView()
    }

    private fun initView() {
        mSendBtn.isShadowEnabled = true
        mSendBtn.shadowHeight = 5
        mSendBtn.cornerRadius = 5
        mSendBtn.onClick { sendAction() }

        mImagePicker.setAttType(BaseConstant.Att_Type_Driving)
        // 上传图片的事件
        mImagePicker.setOnUploadListener(object : ImagePickerView.OnUploadListener {
            override fun onError() {
                buttonEnable(this@DrivingInfoUploadActivity, mSendBtn, true)
                this@DrivingInfoUploadActivity.hideLoading()
                ViewUtils.showSimpleAlert(this@DrivingInfoUploadActivity, "有图片上传失败，请重新确定上传")
            }

            override fun onComplete() {
                buttonEnable(this@DrivingInfoUploadActivity, mSendBtn, true)
                mPresenter.updateDrivingInfo(PISUtil.getInstanceId(), mRemarkEt.text.toString(), uuid, PISUtil.getTokenKey())
            }

        })

    }

    private fun sendAction() {
        if (mRemarkEt.text.toString().isNullOrEmpty()) {
            ViewUtils.warning(this, "请输入备注信息")
            return
        }

        AlertView("提示", "您确定要提交吗?", "取消", arrayOf("确定"), null, this@DrivingInfoUploadActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
            when (position) {
                0 -> {
                    buttonEnable(this, mSendBtn, false)
                    this@DrivingInfoUploadActivity.showLoading()
                    mImagePicker.uploadAction(uuid, BaseConstant.Att_Type_Driving, PISUtil.getTokenKey())
                }
            }
        }).show();
    }

    override fun injectComponent() {
        DaggerDrivingInfoComponent.builder().activityComponent(mActivityComponent).drivingInfoModule(DrivingInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUploadDetailResult(result: CommonReturn) {
        this.hideLoading()
        ViewUtils.success(this, "上报成功")
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    mImagePicker.onPickerDoneResult(selectList)
                }
            }
        }
    }
}