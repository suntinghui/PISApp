package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.ext.isNullOrEmpty
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.ext.setVisible
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.SetoffInfo
import com.lkpower.pis.injection.component.DaggerSetoffComponent
import com.lkpower.pis.injection.module.SetoffModule
import com.lkpower.pis.presenter.SetoffDetailPresenter
import com.lkpower.pis.presenter.view.SetoffDetailView
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.utils.ViewUtils.buttonEnable
import kotlinx.android.synthetic.main.activity_setoff_detail.*

/*
   退乘确认
 */
class SetoffDetailActivity : BaseMvpActivity<SetoffDetailPresenter>(), SetoffDetailView {

    private lateinit var taskId: String
    private lateinit var setoff: SetoffInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setoff_detail)

        taskId = intent.getStringExtra("taskId")

        this.initView()

        queryDetail()
    }

    private fun initView() {
        // 默认不可见
        mOperBtn.setVisible(false)
        mOperBtn.isShadowEnabled = true
        mOperBtn.shadowHeight = 5
        mOperBtn.cornerRadius = 5
        mOperBtn.onClick { setAction() }
    }

    private fun queryDetail() {
        mPresenter.getSetoff(PISUtil.getInstanceId(), taskId, PISUtil.getTokenKey())
    }

    private fun setAction() {
        buttonEnable(this, mOperBtn, false)
        mPresenter.setoffConfirm(taskId, mTaskPlaceTv.text.toString(), PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerSetoffComponent.builder().activityComponent(mActivityComponent).setoffModule(SetoffModule()).build().inject(this)
        mPresenter.mView = this
    }

    // 取得详情
    override fun onGetDetailResult(item: SetoffInfo) {
        setoff = item

        mClassNameView.setContentText(item.ClassName)
        mSendTimeView.setContentText(item.SendTime)
        mSiteNameView.setContentText(item.SiteName)
        mTaskStatusView.setContentText(if (item.TaskStatus == "0") "待执行" else "已完成")
        mOverConfirmTimeView.setContentText(item.OverConfirmTIme)
        mBeginTimeView.setContentText(item.BeginTime)

        mTaskPlaceView.setContentText(item.TaskPlace)
        mTaskPlaceTv.text = if (item.TaskPlaceSource == null || item.TaskPlaceSource.isNullOrEmpty()) "没有退乘地点" else item.TaskPlaceSource.first()
        mTaskPlaceTv.onClick { selectTaskPlace() }

        refreshStatus()
    }

    private fun selectTaskPlace() {
        if (setoff.TaskPlaceSource == null || setoff.TaskPlaceSource.isNullOrEmpty()) {
            ViewUtils.warning(this, "没有退乘地点可供选择")
            return
        }

        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mTaskPlaceTv.text = setoff.TaskPlaceSource.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(setoff.TaskPlaceSource)
        pickerView.setSelectOptions(setoff.TaskPlaceSource.indexOf(mTaskPlaceTv.text.toString()))
        pickerView.show()
    }

    // 项目确认
    override fun setOffResult(result: CommonReturn) {
        ViewUtils.success(this, "操作成功")
        queryDetail()
    }

    override fun setOffComplete() {
        buttonEnable(this, mOperBtn, true)
    }

    private fun refreshStatus() {
        // 任务状态:0=待执行，1=执行中，2=执行完成

        var done = setoff.TaskStatus == "2"

        mOperBtn.setVisible(done.not())
        mTaskPlaceLayout.setVisible(done.not())
        mTaskPlaceView.setVisible(done)
    }
}