package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.google.gson.Gson
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.utils.DateUtils
import com.lkpower.pis.widgets.ImagePickerView
import com.lkpower.pis.widgets.LabelTextView
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.ext.setVisible
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.FaultInfo
import com.lkpower.pis.data.protocol.FaultInfoConfirm
import com.lkpower.pis.injection.component.DaggerFaultInfoComponent
import com.lkpower.pis.injection.module.FaultInfoModule
import com.lkpower.pis.presenter.FaultInfoDetailPresenter
import com.lkpower.pis.presenter.view.FaultInfoDetailView
import com.lkpower.pis.utils.PISUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_fault_history_confirm.*
import org.jetbrains.anko.toast
import java.lang.Exception

class FaultHistoryConfirmActivity : BaseMvpActivity<FaultInfoDetailPresenter>(), FaultInfoDetailView {

    private val CONFIRM_STATUS_LIS = listOf<String>("未修复", "已修复")

    private lateinit var faultInfo: FaultInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fault_history_confirm)

        initView()

        loadData()
    }

    private fun initView() {
        mConfirmTv.text = CONFIRM_STATUS_LIS.first()
        mConfirmTv.onClick { selectConfirmStatus() }

        mSendBtn.setShadowEnabled(true)
        mSendBtn.setShadowHeight(5)
        mSendBtn.setCornerRadius(5)
        mSendBtn.onClick { sendAction() }

        mShowImageView.setEditable(false)
                .setVisible(false)
    }

    private fun loadData() {
        mPresenter.getFaultInfoModel(intent.getStringExtra("Id"), PISUtil.getTokenKey())
    }

    // 确认修复提交事件
    private fun sendAction() {
        if (mRemarkEt.text.toString().isNullOrEmpty()) {
            ViewUtils.warning(this, "请输入修复备注说明")
            return
        }

        AlertView("确认提交？", "您选中的状态为：${mConfirmTv.text}", "取消", arrayOf("确定"), null, this@FaultHistoryConfirmActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
            when (position) {
                0 -> {
                    mImagePicker.uploadAction(faultInfo.FaultId, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
                }
            }
        }).show();

        mImagePicker.setOnUploadListener(object : ImagePickerView.OnUploadListener {
            override fun onError() {
                this@FaultHistoryConfirmActivity.hideLoading()
                ViewUtils.showSimpleAlert(this@FaultHistoryConfirmActivity, "有图片上传失败，请重新确定上传")
            }

            override fun onComplete() {
                try {
                    var newConfirm = FaultInfoConfirm(faultInfo.ConfirmInfo.ID, faultInfo.FaultId, CONFIRM_STATUS_LIS.indexOf(mConfirmTv.text).toString(), "", "", mRemarkEt.text.toString(), DateUtils.getNow("yyyy-MM-dd HH:mm:ss"))
                    mPresenter.addFaultInfoConfirm(Gson().toJson(newConfirm), PISUtil.getTokenKey())
                } catch (e: Exception) {
                    ViewUtils.showSimpleAlert(this@FaultHistoryConfirmActivity, "数据异常,请稍候重试")
                    e.printStackTrace()
                }
            }

        })

    }

    // 取得详情的事件
    override fun onGetDetailResult(result: FaultInfo) {
        faultInfo = result

        // 如果faultInfo的ConfirmInfo为null，这说明是还没有创建确认对象，App来手动创建
        if (faultInfo.ConfirmInfo == null) {
            faultInfo.ConfirmInfo = FaultInfoConfirm(PISUtil.getUUID(), faultInfo.FaultId, "0", "", "", "", "")
        }

        try {
            // 查询故障图片
            mPresenter.getAttList(faultInfo.FaultId, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
            // 查询确认修复图片
            mPresenter.getAttList(faultInfo.ConfirmInfo.ID, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())

        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            updateEditState()

            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("车号", result.TrainNo))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("故障配件", result.PartName))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("故障类型", result.FaultTypeName))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("故障说明", result.Remark))
            mDetailLayout.addView(LabelTextView(this).setLabelAndContent("上报时间", result.ReportTime))

            mConfirmTv.text = if (faultInfo.ConfirmInfo?.Result == "0") "未修复" else "已修复"
            mRemarkEt.text = Editable.Factory.getInstance().newEditable(faultInfo.ConfirmInfo?.Remark)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // 得到附件
    override fun onGetAttListResult(result: List<AttModel>) {
        if (result.isEmpty())
            return

        // 根据busID来判断是故障的图片
        if (result.first().BusId == faultInfo.FaultId) {
            mShowImageView.setVisible(true)
            mShowImageView.setEditable(false)
                    .setAttType(BaseConstant.Att_Type_Other)
                    .setNetImages(result)

        } else if (result.first().BusId == faultInfo.ConfirmInfo.ID) {
            mImagePicker.setAttType(BaseConstant.Att_Type_Other)
                    .setNetImages(result)
        }

    }

    // 确认修复结果
    override fun onConfirmResult(result: CommonReturn) {
        this.hideLoading()
        ViewUtils.success(this, "已成功提交")
        finish()
    }

    private fun updateEditState() {
        var editable = (faultInfo.HandleResult == "3").not() // 3 已处理
        mSendBtn.setVisible(editable)
        mImagePicker.setEditable(editable)
    }

    private fun selectConfirmStatus() {
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mConfirmTv.text = CONFIRM_STATUS_LIS.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(CONFIRM_STATUS_LIS)
        pickerView.setSelectOptions(CONFIRM_STATUS_LIS.indexOf(mConfirmTv.text))
        pickerView.show()
    }

    override fun injectComponent() {
        DaggerFaultInfoComponent.builder().activityComponent(mActivityComponent).faultInfoModule(FaultInfoModule()).build().inject(this)
        mPresenter.mView = this
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