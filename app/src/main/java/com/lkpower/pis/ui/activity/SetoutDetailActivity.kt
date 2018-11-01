package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.lkpower.base.utils.PISUtil
import com.lkpower.base.ext.onClick
import com.lkpower.base.ext.setVisible
import com.lkpower.base.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutDetailPresenter
import com.lkpower.pis.presenter.view.SetoutDetailView
import kotlinx.android.synthetic.main.activity_setout_detail.*
import org.jetbrains.anko.toast

/*
   出乘确认
 */
class SetoutDetailActivity : BaseMvpActivity<SetoutDetailPresenter>(), SetoutDetailView {

    private lateinit var taskId:String

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
        mPresenter.setOutConfirm(taskId, PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    // 取得详情
    override fun onGetDetailResult(item: SetoutInfo) {
        mClassNameView.setContentText(item.ClassName)
        mSendTimeView.setContentText(item.SendTime)
        mSiteNameView.setContentText(item.SiteName)
        mTaskStatusView.setContentText(if (item.TaskStatus == "0") "待执行" else "已完成")
        mOverConfirmTimeView.setContentText(item.OverConfirmTIme)
        mBeginTimeView.setContentText(item.BeginTime)

        // 任务状态:0=待执行，1=执行中，2=执行完成
        mOperBtn.setVisible(item.TaskStatus == "0")
    }

    // 项目确认
    override fun setOutResult(result: CommonReturn) {
        ViewUtils.success(this, "项目确认成功")
        queryDetail()
    }
}