package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.TaskConveyDetail
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.TaskConveyDetailPresenter
import com.lkpower.pis.presenter.view.TaskConveyDetailView
import com.lkpower.pis.ui.adapter.RiskItemAdapter
import kotlinx.android.synthetic.main.activity_taskconvey_detail.*
import org.jetbrains.anko.toast

class TaskConveyDetailActivity : BaseMvpActivity<TaskConveyDetailPresenter>(), TaskConveyDetailView {

    private var ConveyDetailId: String = ""
    private lateinit var mAdapter: RiskItemAdapter
    private lateinit var mDetail: TaskConveyDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_taskconvey_detail)

        ConveyDetailId = intent.getStringExtra("ConveyDetailId")

        initView()

        loadDetail()
    }

    private fun initView() {
        mRiskItemsRv.layoutManager = LinearLayoutManager(this)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mRiskItemsRv);

        mAdapter = RiskItemAdapter(this)
        mRiskItemsRv.adapter = mAdapter

        mAdapter.setOnSubmitRiskItemListener(object : RiskItemAdapter.SubmitRiskItemListener {
            override fun onSubmit(position: Int, feedback: String) {
                AlertView("提示", "已确认信息无误，立即提交？", "取消", arrayOf("确定"), null, this@TaskConveyDetailActivity, AlertView.Style.Alert, OnItemClickListener { o, index ->
                    when (index) {
                        0 -> {
                            mPresenter.taskRiskItemConfirm(mDetail.RiskItems.get(position).ItemId, feedback, AppPrefsUtils.getString(BaseConstant.kTokenKey))
                        }
                    }
                }).show()
            }

        })
    }

    private fun loadDetail() {
        mPresenter.getTaskConveyDetail(ConveyDetailId, AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }


    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetDetailResult(result: TaskConveyDetail) {
        this.mDetail = result

        mTitleView.setContentText(result.TaskTitle)
        mContentView.setContentText(result.TaskContent)
        mSetOutTypeNameView.setContentText(result.SetOutTypeName)
        mCompleteTimeView.setContentText(result.CompleteTime)
        mPublisherNameView.setContentText(result.PublisherName)
        mPublishDateView.setContentText(result.PublishDate)
    }

    override fun onConfirmResult(result: String) {
        toast("提交成功")

        loadDetail()
    }


}