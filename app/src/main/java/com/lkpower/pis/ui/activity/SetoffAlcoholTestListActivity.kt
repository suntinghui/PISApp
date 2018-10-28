package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoffAlcoholTest
import com.lkpower.pis.injection.component.DaggerSetoffComponent
import com.lkpower.pis.injection.module.SetoffModule
import com.lkpower.pis.presenter.SetoffAlcoholTestListPresenter
import com.lkpower.pis.presenter.view.SetoffAlcoholTestListView
import com.lkpower.pis.ui.adapter.SetoffAlcoholTestAdapter
import com.lkpower.pis.utils.PISUtil
import kotlinx.android.synthetic.main.activity_setout_checkin_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/SetoffAlcoholTestListActivity")
class SetoffAlcoholTestListActivity : BaseMvpActivity<SetoffAlcoholTestListPresenter>(), SetoffAlcoholTestListView {

    private lateinit var mAdapter: SetoffAlcoholTestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_checkin_list)

        initView()
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getSetoffAlcoholTestList(PISUtil.getInstanceId(), PISUtil.getTokenKey())
    }

    private fun initView() {
        mHeaderBar.setTitleText("酒测列表")

        mCheckinRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mCheckinRv);

        mAdapter = SetoffAlcoholTestAdapter(this)
        mCheckinRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoffAlcoholTest> {
            override fun onItemClick(item: SetoffAlcoholTest, position: Int) {
                startActivity<SetoffAlcoholTestDetailActivity>("instanceId" to item.SetOffInstanceId, "taskId" to item.ID)
            }
        })
    }

    override fun onDataIsNull() {
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun injectComponent() {
        DaggerSetoffComponent.builder().activityComponent(mActivityComponent).setoffModule(SetoffModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<SetoffAlcoholTest>) {
        mAdapter.setData(result.toMutableList())

        if (result.isNotEmpty())
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        else
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }
}