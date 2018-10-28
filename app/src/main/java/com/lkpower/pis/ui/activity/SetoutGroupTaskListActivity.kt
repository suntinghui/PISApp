package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.utils.PISUtil
import com.lkpower.base.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutGroupTask
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutGroupTaskListPresenter
import com.lkpower.pis.presenter.view.SetoutGroupTaskListView
import com.lkpower.pis.ui.adapter.SetoutGroupTaskAdapter
import kotlinx.android.synthetic.main.activity_setout_grouptask_list.*
import org.jetbrains.anko.startActivity

/*
出乘管理-项目确认的列表
 */
@Route(path = "/pis/SetoutGroupTaskListActivity")
class SetoutGroupTaskListActivity : BaseMvpActivity<SetoutGroupTaskListPresenter>(), SetoutGroupTaskListView {

    private lateinit var mAdapter: SetoutGroupTaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_grouptask_list)

        initView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun initView() {
        mHeaderBar.setTitleText("项目确认列表")

        mTaskRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mTaskRv);

        mAdapter = SetoutGroupTaskAdapter(this)
        mTaskRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoutGroupTask> {
            override fun onItemClick(item: SetoutGroupTask, position: Int) {
                startActivity<SetoutConfirmProjListActivity>("GroupTaskId" to item.ID)
            }
        })
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getSetoutCheckinList(PISUtil.getInstanceId(), PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<SetoutGroupTask>) {
        mAdapter.setData(result.toMutableList())

        if (result.isNotEmpty())
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        else
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }


}