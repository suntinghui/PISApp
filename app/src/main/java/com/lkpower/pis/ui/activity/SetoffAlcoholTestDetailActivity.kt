package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import com.lkpower.pis.data.protocol.SetoffAlcoholTest
import com.lkpower.pis.injection.component.DaggerSetoffComponent
import com.lkpower.pis.injection.module.SetoffModule
import com.lkpower.pis.presenter.SetoffAlcoholTestDetailPresenter
import com.lkpower.pis.presenter.view.SetoffAlcoholTestDetailView
import com.lkpower.pis.utils.PISUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_setout_alcoholtest_detail.*
import org.jetbrains.anko.toast

class SetoffAlcoholTestDetailActivity : BaseMvpActivity<SetoffAlcoholTestDetailPresenter>(), SetoffAlcoholTestDetailView {

    // 0不通过 1通过
    val RESULT_LIST = listOf<String>("酒测不通过", "酒测通过")

    private lateinit var instanceId: String
    private lateinit var taskId: String

    private lateinit var setoffAlcoholTest: SetoffAlcoholTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_alcoholtest_detail)

        instanceId = intent.getStringExtra("instanceId")
        taskId = intent.getStringExtra("taskId")

        this.initView()

        queryDetail()
    }

    private fun initView() {
        mResultTv.text = RESULT_LIST.get(0)
        mResultTv.onClick { this.showResultEvent() }

        mOperBtn.isShadowEnabled = true
        mOperBtn.shadowHeight = 5
        mOperBtn.cornerRadius = 5
        mOperBtn.onClick { setAction() }

        mImagePicker.setAttType(BaseConstant.Att_Type_Other)
        // 上传图片的事件
        mImagePicker.setOnUploadListener(object : ImagePickerView.OnUploadListener {
            override fun onError() {
                this@SetoffAlcoholTestDetailActivity.hideLoading()
                ViewUtils.showSimpleAlert(this@SetoffAlcoholTestDetailActivity, "有图片上传失败，请重新确定上传")
            }

            override fun onComplete() {
                mPresenter.setoffAlcoholTest(taskId, RESULT_LIST.indexOf(mResultTv.text).toString(), PISUtil.getTokenKey())
            }

        })
    }

    private fun queryDetail() {
        mPresenter.getSetOffAlcoholTest(instanceId, taskId, PISUtil.getTokenKey())
    }

    private fun queryAtt() {
        mPresenter.getAttList(setoffAlcoholTest.ID, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
    }

    private fun setAction() {
        AlertView("确认提交", "当前状态为${mResultTv.text}", "取消", arrayOf("确定"), null, this@SetoffAlcoholTestDetailActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
            when (position) {
                0 -> {
                    this@SetoffAlcoholTestDetailActivity.showLoading()
                    mImagePicker.uploadAction(setoffAlcoholTest.ID, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
                }
            }
        }).show();
    }

    override fun injectComponent() {
        DaggerSetoffComponent.builder().activityComponent(mActivityComponent).setoffModule(SetoffModule()).build().inject(this)
        mPresenter.mView = this
    }

    // 选择状态
    private fun showResultEvent() {
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mResultTv.text = RESULT_LIST.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(RESULT_LIST)
        pickerView.setSelectOptions(RESULT_LIST.indexOf(mResultTv.text))
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

    override fun onGetAttListResult(result: List<AttModel>) {
        mImagePicker.setNetImages(result)
    }

    // 取得详情
    override fun onGetDetailResult(item: SetoffAlcoholTest) {
        try {
            setoffAlcoholTest = item

            // 任务状态:0=待执行，1=执行中，2=执行完成
            mImagePicker.setEditable(setoffAlcoholTest.TaskStatus == "0")

            mClassNameView.setContentText(item.ClassName)
            mSendTimeView.setContentText(item.SendTime)
            mSiteNameView.setContentText(item.SiteName)
            mTaskStatusView.setContentText(if (item.TaskStatus == "0") "待执行" else "已完成")
            mResultView.setContentText(if (item.Result == "0") "不通过" else "通过") // 不通过=0，通过=1
            mLatesttAlcoholTestTimeView.setContentText(item.LatesttAlcoholTestTime)
            mBeginTimeView.setContentText(item.BeginTime)

            mTestLayout.setVisible(setoffAlcoholTest.TaskStatus == "0")
            mOperBtn.setVisible(setoffAlcoholTest.TaskStatus == "0")
            mResultView.setVisible(setoffAlcoholTest.TaskStatus != "0")


            // 取到busId后才可以查询附件
            queryAtt()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 报到
    override fun setOffResult(result: Boolean) {
        this.hideLoading()
        ViewUtils.success(this, "酒测上报成功")
        queryDetail()
    }
}