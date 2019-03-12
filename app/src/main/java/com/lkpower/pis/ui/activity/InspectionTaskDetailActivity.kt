package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.widgets.ImagePickerView
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.ext.setVisible
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.MissionStateInfo
import com.lkpower.pis.injection.component.DaggerInspectionComponent
import com.lkpower.pis.injection.module.InspectionModule
import com.lkpower.pis.presenter.InspectionTaskDetailPresenter
import com.lkpower.pis.presenter.view.InspectionTaskDetailView
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils.buttonEnable
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_inspection_task_detail.*
import org.jetbrains.anko.toast

class InspectionTaskDetailActivity : BaseMvpActivity<InspectionTaskDetailPresenter>(), InspectionTaskDetailView {

    // 未完成是3 已完成是4
    private val TASK_STATUS_LIS = listOf<String>("未完成", "已完成") // 不得随意更换顺序

    private lateinit var taskId: String
    private lateinit var missionStateInfo: MissionStateInfo

    private lateinit var tempRemark:String // 选择图片后会导致输入的文字丢失，所以进行暂存

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection_task_detail)

        taskId = intent.getStringExtra("TaskId")

        initView()

        loadData()
    }

    private fun initView() {

        mStateTv.text = TASK_STATUS_LIS.first()
        mStateTv.onClick { selectTaskStatus() }

        mSendBtn.isShadowEnabled = true
        mSendBtn.shadowHeight = 5
        mSendBtn.cornerRadius = 5
        mSendBtn.onClick { sendAction() }

        mImagePicker.setAttType(BaseConstant.Att_Type_Inspection)
        // 上传图片的事件
        mImagePicker.setOnUploadListener(object : ImagePickerView.OnUploadListener {
            override fun onError() {
                buttonEnable(this@InspectionTaskDetailActivity, mSendBtn, true)
                this@InspectionTaskDetailActivity.hideLoading()
                ViewUtils.showSimpleAlert(this@InspectionTaskDetailActivity, "有图片上传失败，请重新确定上传")
            }

            override fun onComplete() {
                buttonEnable(this@InspectionTaskDetailActivity, mSendBtn, true)
                mPresenter.updateMissionInfoExt(taskId, (TASK_STATUS_LIS.indexOf(mStateTv.text) + 3).toString(), mRemarkEt.text.toString(), PISUtil.getTokenKey())
            }

        })

    }

    private fun loadData() {
        this.showLoading()
        mPresenter.getXJTaskModel(taskId, PISUtil.getTokenKey())
    }

    private fun queryAtt() {
        mPresenter.getAttList(missionStateInfo.ID, BaseConstant.Att_Type_Inspection, PISUtil.getTokenKey())
    }

    private fun sendAction() {
        if (mRemarkEt.text.toString().isNullOrEmpty()) {
            ViewUtils.warning(this, "请输入备注信息")
            return
        }

        AlertView("确认提交？", "您选中的状态为：${mStateTv.text}", "取消", arrayOf("确定"), null, this@InspectionTaskDetailActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
            when (position) {
                0 -> {
                    buttonEnable(this, mSendBtn, false)
                    this@InspectionTaskDetailActivity.showLoading()
                    mImagePicker.uploadAction(missionStateInfo.ID, BaseConstant.Att_Type_Inspection, PISUtil.getTokenKey())
                }
            }
        }).show();
    }

    private fun selectTaskStatus() {
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mStateTv.text = TASK_STATUS_LIS.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(TASK_STATUS_LIS)
        pickerView.setSelectOptions(TASK_STATUS_LIS.indexOf(mStateTv.text))
        pickerView.show()
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

    override fun injectComponent() {
        DaggerInspectionComponent.builder().activityComponent(mActivityComponent).inspectionModule(InspectionModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetDetailResult(result: MissionStateInfo) {
        this.hideLoading()

        missionStateInfo = result

        if (result.remark.isNotEmpty())
            mRemarkEt.text = Editable.Factory.getInstance().newEditable(result.remark)

        mTitleView.setContentText(result.MissionName)
        mContentView.setContentText(result.MissionRemark)
        mRemarkView.setContentText(result.remark)
        mStateView.setContentText(PISUtil.getInspectionTaskState(result.state))

        refreshViewVisible(result.state)

        queryAtt()
    }

    override fun onUpdateMissionInfo(result: Boolean) {
        ViewUtils.success(this, "操作成功")
        loadData()
    }

    override fun onGetAttListResult(result: List<AttModel>) {
        mImagePicker.setNetImages(result)
    }

    private fun refreshViewVisible(state: String) {
        var done = state == "4"
        mStateLayout.setVisible(done.not())
        mRemarkLayout.setVisible(done.not())
        mSendBtn.setVisible(done.not())
        mImagePicker.setEditable(done.not())

        mRemarkView.setVisible(done)
        mStateView.setVisible(done)

    }
}