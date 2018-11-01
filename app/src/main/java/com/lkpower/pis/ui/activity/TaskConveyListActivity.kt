package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.utils.PISUtil
import com.lkpower.base.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.TaskConveyDetail
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.TaskConveyListPresenter
import com.lkpower.pis.presenter.view.TaskConveyListView
import com.lkpower.pis.ui.adapter.TaskConveyAdapter
import kotlinx.android.synthetic.main.activity_taskconvey_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/TaskConveyListActivity")
class TaskConveyListActivity : BaseMvpActivity<TaskConveyListPresenter>(), TaskConveyListView {

    private lateinit var mAdapter: TaskConveyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_taskconvey_list)

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

        mAdapter = TaskConveyAdapter(this)
        mTaskRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<TaskConveyDetail> {
            override fun onItemClick(item: TaskConveyDetail, position: Int) {
                startActivity<TaskConveyDetailActivity>("ConveyDetailId" to item.ConveyDetailId)
            }
        })
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getTaskConveyList(PISUtil.getInstanceId(), PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<TaskConveyDetail>) {
        mAdapter.setData(result.toMutableList())

        if (result.isNotEmpty())
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        else
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }
}