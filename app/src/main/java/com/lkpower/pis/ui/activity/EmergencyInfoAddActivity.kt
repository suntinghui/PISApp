package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.google.gson.Gson
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.utils.DateUtils
import com.lkpower.pis.widgets.ImagePickerView
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.EmergencyInfo
import com.lkpower.pis.injection.component.DaggerEmergencyInfoComponent
import com.lkpower.pis.injection.module.EmergencyInfoModule
import com.lkpower.pis.presenter.EmergencyInfoAddPresenter
import com.lkpower.pis.presenter.view.EmergencyInfoAddView
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils.buttonEnable
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_driving_info_upload.*
import org.jetbrains.anko.toast

@Route(path = "/pis/EmergencyInfoAddActivity")
class EmergencyInfoAddActivity : BaseMvpActivity<EmergencyInfoAddPresenter>(), EmergencyInfoAddView {

    private lateinit var uuid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_emergency_info_add)

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
                buttonEnable(this@EmergencyInfoAddActivity, mSendBtn, true)
                this@EmergencyInfoAddActivity.hideLoading()
                ViewUtils.showSimpleAlert(this@EmergencyInfoAddActivity, "有图片上传失败，请重新确定上传")
            }

            override fun onComplete() {
                buttonEnable(this@EmergencyInfoAddActivity, mSendBtn, true)
                var info = EmergencyInfo(uuid, PISUtil.getInstanceId(), "", "", DateUtils.getNow("yyyy-MM-dd HH:mm:ss"), mRemarkEt.text.toString(), "", "", "", "")
                mPresenter.addEmergencyInfo(Gson().toJson(info), PISUtil.getTokenKey())
            }
        })

    }

    private fun sendAction() {
        if (mRemarkEt.text.toString().isNullOrEmpty()) {
            ViewUtils.warning(this, "请输入备注信息")
            return
        }

        AlertView("提示", "您确定要提交吗?", "取消", arrayOf("确定"), null, this@EmergencyInfoAddActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
            when (position) {
                0 -> {
                    buttonEnable(this, mSendBtn, false)
                    this@EmergencyInfoAddActivity.showLoading()
                    mImagePicker.uploadAction(uuid, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
                }
            }
        }).show();
    }

    override fun injectComponent() {
        DaggerEmergencyInfoComponent.builder().activityComponent(mActivityComponent).emergencyInfoModule(EmergencyInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUploadDetailResult(result: CommonReturn) {
        this.hideLoading()
        ViewUtils.success(this, "反馈成功")
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