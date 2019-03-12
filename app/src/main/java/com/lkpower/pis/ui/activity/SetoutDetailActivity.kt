package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.ext.isNullOrEmpty
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.ext.setVisible
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutDetailPresenter
import com.lkpower.pis.presenter.view.SetoutDetailView
import com.lkpower.pis.utils.ViewUtils.buttonEnable
import kotlinx.android.synthetic.main.activity_setout_detail.*

/*
   出乘确认
 */
class SetoutDetailActivity : BaseMvpActivity<SetoutDetailPresenter>(), SetoutDetailView {

    private lateinit var taskId: String
    private lateinit var setout: SetoutInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_detail)

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
        mPresenter.getSetoutDetail(PISUtil.getInstanceId(), taskId, PISUtil.getTokenKey())
    }

    private fun setAction() {
        buttonEnable(this, mOperBtn, false)
        mPresenter.setOutConfirm(taskId, mTaskPlaceTv.text.toString(), PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    // 取得详情
    override fun onGetDetailResult(item: SetoutInfo) {
        setout = item

        mClassNameView.setContentText(item.ClassName)
        mSendTimeView.setContentText(item.SendTime)
        mSiteNameView.setContentText(item.SiteName)
        mTaskStatusView.setContentText(if (item.TaskStatus == "0") "待执行" else "已完成")
        mOverConfirmTimeView.setContentText(item.OverConfirmTIme)
        mBeginTimeView.setContentText(item.BeginTime)

        mTaskPlaceView.setContentText(item.TaskPlace)
        mTaskPlaceTv.text = if (item.TaskPlaceSource == null || item.TaskPlaceSource.isNullOrEmpty()) "没有出乘地点" else item.TaskPlaceSource.first()
        mTaskPlaceTv.onClick { selectTaskPlace() }

        refreshStatus()
    }

    private fun selectTaskPlace() {
        if (setout.TaskPlaceSource == null || setout.TaskPlaceSource.isNullOrEmpty()) {
            ViewUtils.warning(this, "没有出乘地点可选择")
            return
        }

        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mTaskPlaceTv.text = setout.TaskPlaceSource.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(setout.TaskPlaceSource)
        pickerView.setSelectOptions(setout.TaskPlaceSource.indexOf(mTaskPlaceTv.text.toString()))
        pickerView.show()
    }


    override fun setOutResult(result: CommonReturn) {
        ViewUtils.success(this, "操作成功")
        queryDetail()
    }

    override fun setOutComplete() {
        buttonEnable(this, mOperBtn, true)
    }

    private fun refreshStatus() {
        // 任务状态:0=待执行，1=执行中，2=执行完成

        var done = setout.TaskStatus == "2"

        mOperBtn.setVisible(done.not())
        mTaskPlaceLayout.setVisible(done.not())
        mTaskPlaceView.setVisible(done)
    }
}