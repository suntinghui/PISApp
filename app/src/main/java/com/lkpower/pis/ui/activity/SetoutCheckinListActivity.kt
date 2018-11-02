package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kennyc.view.MultiStateView
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutCheckInListPresenter
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.ui.adapter.SetoutCheckinAdapter
import kotlinx.android.synthetic.main.activity_setout_checkin_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/SetoutCheckinListActivity")
class SetoutCheckinListActivity : BaseMvpActivity<SetoutCheckInListPresenter>(), SetoutCheckinListView {

    private lateinit var mAdapter: SetoutCheckinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_checkin_list)

        initView()

    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    private fun initView() {
        mHeaderBar.setTitleText("出乘报到")

        mCheckinRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mCheckinRv);

        mAdapter = SetoutCheckinAdapter(this)
        mCheckinRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoutCheckIn> {
            override fun onItemClick(item: SetoutCheckIn, position: Int) {
                startActivity<SetoutCheckinDetailActivity>("instanceId" to item.SetOutInstanceId, "taskId" to item.ID)
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

    override fun onGetListResult(result: List<SetoutCheckIn>) {
        mAdapter.setData(result.toMutableList())

        if (result.isNotEmpty())
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        else
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }
}