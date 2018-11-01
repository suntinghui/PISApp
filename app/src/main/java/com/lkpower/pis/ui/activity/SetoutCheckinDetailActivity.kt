package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.lkpower.base.utils.PISUtil
import com.lkpower.base.ext.onClick
import com.lkpower.base.ext.setVisible
import com.lkpower.base.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutCheckinDetailPresenter
import com.lkpower.pis.presenter.view.SetoutCheckinDetailView
import kotlinx.android.synthetic.main.activity_setout_checkin_detail.*
import org.jetbrains.anko.toast
import java.lang.Exception

/*
   出乘任务报道详情，包含报到操作
 */
class SetoutCheckinDetailActivity : BaseMvpActivity<SetoutCheckinDetailPresenter>(), SetoutCheckinDetailView {

    private lateinit var instanceId: String
    private lateinit var taskId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_checkin_detail)

        instanceId = intent.getStringExtra("instanceId")
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
        mPresenter.getSetoutCheckinDetail(instanceId, taskId, PISUtil.getTokenKey())
    }

    private fun setAction() {
        mPresenter.setOutCheckin(taskId, PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    // 取得详情
    override fun onGetDetailResult(item: SetoutCheckIn) {
        try {
            mClassNameView.setContentText(item.ClassName)
            mSendTimeView.setContentText(item.SendTime)
            mSiteNameView.setContentText(item.SiteName)
            mTaskStatusView.setContentText(if (item.TaskStatus == "0") "待执行" else "已完成")
            mLatestCheckInTimeView.setContentText(item.LatestCheckInTime)
            mBeginTimeView.setContentText(item.BeginTime)

            // 任务状态:0=待执行，1=执行中，2=执行完成
            mOperBtn.setVisible(item.TaskStatus == "0")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 报到
    override fun setOutResult(result: Boolean) {
        ViewUtils.success(this, "出乘报到成功")
        queryDetail()
    }
}