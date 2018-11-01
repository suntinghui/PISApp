package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoffInfo
import com.lkpower.pis.injection.component.DaggerSetoffComponent
import com.lkpower.pis.injection.module.SetoffModule
import com.lkpower.pis.presenter.SetoffListPresenter
import com.lkpower.pis.presenter.view.SetoffListView
import com.lkpower.pis.ui.adapter.SetoffAdapter
import com.lkpower.base.utils.PISUtil
import kotlinx.android.synthetic.main.activity_setout_checkin_list.*
import org.jetbrains.anko.startActivity

/*
出乘确认(出乘管理最后一项)列表
 */
@Route(path = "/pis/SetoffListActivity")
class SetoffListActivity : BaseMvpActivity<SetoffListPresenter>(), SetoffListView {

    private lateinit var mAdapter: SetoffAdapter

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
        mHeaderBar.setTitleText("退乘列表")

        mCheckinRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mCheckinRv);

        mAdapter = SetoffAdapter(this)
        mCheckinRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoffInfo> {
            override fun onItemClick(item: SetoffInfo, position: Int) {
                startActivity<SetoffDetailActivity>("taskId" to item.SetOffInstanceId)
            }
        })
    }

    private fun queryList() {
        mPresenter.getSetoffList(PISUtil.getInstanceId(), PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerSetoffComponent.builder().activityComponent(mActivityComponent).setoffModule(SetoffModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<SetoffInfo>) {
        mAdapter.setData(result.toMutableList())

        if (result.isNotEmpty())
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        else
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }
}