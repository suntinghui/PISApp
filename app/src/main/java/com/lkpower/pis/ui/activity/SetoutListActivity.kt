package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kennyc.view.MultiStateView
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutListPresenter
import com.lkpower.pis.presenter.view.SetoutListView
import com.lkpower.pis.ui.adapter.SetoutAdapter
import kotlinx.android.synthetic.main.activity_setout_checkin_list.*
import org.jetbrains.anko.startActivity

/*
出乘确认(出乘管理最后一项)列表
 */
@Route(path = "/pis/SetoutListActivity")
class SetoutListActivity : BaseMvpActivity<SetoutListPresenter>(), SetoutListView {

    private lateinit var mAdapter: SetoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_checkin_list)

        initView()
    }

    override fun onResume() {
        super.onResume()

        queryList()
    }

    private fun initView() {
        mHeaderBar.setTitleText("出乘列表")

        mCheckinRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mCheckinRv);

        mAdapter = SetoutAdapter(this)
        mCheckinRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoutInfo> {
            override fun onItemClick(item: SetoutInfo, position: Int) {
                startActivity<SetoutDetailActivity>("taskId" to item.SetOutInstanceId)
            }
        })
    }

    private fun queryList() {
        mPresenter.getSetoutList(PISUtil.getInstanceId(), PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<SetoutInfo>) {
        mAdapter.setData(result.toMutableList())

        if (result.isNotEmpty())
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        else
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }
}