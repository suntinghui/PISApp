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
import com.lkpower.pis.data.protocol.SetoffCheckIn
import com.lkpower.pis.injection.component.DaggerSetoffComponent
import com.lkpower.pis.injection.module.SetoffModule
import com.lkpower.pis.presenter.SetoffCheckInListPresenter
import com.lkpower.pis.presenter.view.SetoffCheckinListView
import com.lkpower.pis.ui.adapter.SetoffCheckinAdapter
import kotlinx.android.synthetic.main.activity_setout_checkin_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/SetoffCheckinListActivity")
class SetoffCheckinListActivity : BaseMvpActivity<SetoffCheckInListPresenter>(), SetoffCheckinListView {

    private lateinit var mAdapter: SetoffCheckinAdapter

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
        mHeaderBar.setTitleText("退乘报到")

        mCheckinRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mCheckinRv);

        mAdapter = SetoffCheckinAdapter(this)
        mCheckinRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoffCheckIn> {
            override fun onItemClick(item: SetoffCheckIn, position: Int) {
                startActivity<SetoffCheckinDetailActivity>("instanceId" to item.SetOffInstanceId, "taskId" to item.ID)
            }
        })
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getSetoffCheckinList(PISUtil.getInstanceId(), PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerSetoffComponent.builder().activityComponent(mActivityComponent).setoffModule(SetoffModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<SetoffCheckIn>) {
        mAdapter.setData(result.toMutableList())

        if (result.isNotEmpty())
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        else
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }
}