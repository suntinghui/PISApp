package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.MissionStateInfo
import com.lkpower.pis.injection.component.DaggerInspectionComponent
import com.lkpower.pis.injection.module.InspectionModule
import com.lkpower.pis.presenter.InspectionTaskListPresenter
import com.lkpower.pis.presenter.view.InspectionTaskListView
import com.lkpower.pis.ui.adapter.InspectionTaskAdapter
import kotlinx.android.synthetic.main.activity_inspection_task_list.*
import org.jetbrains.anko.startActivity

class InspectionTaskListActivity : BaseMvpActivity<InspectionTaskListPresenter>(), InspectionTaskListView {

    private lateinit var mAdapter: InspectionTaskAdapter
    private lateinit var siteId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_inspection_task_list)

        siteId = intent.getStringExtra("SiteId")

        initView()
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    private fun initView() {
        mTaskRv.layoutManager = LinearLayoutManager(this)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mTaskRv);

        mAdapter = InspectionTaskAdapter(this)
        mTaskRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<MissionStateInfo> {
            override fun onItemClick(item: MissionStateInfo, position: Int) {
                startActivity<InspectionTaskDetailActivity>("TaskId" to item.ID)
            }
        })
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getXJTaskList(AppPrefsUtils.getString(BaseConstant.kInstanceId), siteId, AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }

    override fun injectComponent() {
        DaggerInspectionComponent.builder().activityComponent(mActivityComponent).inspectionModule(InspectionModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<MissionStateInfo>) {
        mAdapter.setData(result.toMutableList())
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
    }

    override fun onDataIsNull() {
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }
}