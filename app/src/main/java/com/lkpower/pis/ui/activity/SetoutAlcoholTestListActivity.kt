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
import com.lkpower.pis.data.protocol.SetoutAlcoholTest
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutAlcoholTestListPresenter
import com.lkpower.pis.presenter.view.SetoutAlcoholTestListView
import com.lkpower.pis.ui.adapter.SetoutAlcoholTestAdapter
import kotlinx.android.synthetic.main.activity_setout_checkin_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/SetoutAlcoholTestListActivity")
class SetoutAlcoholTestListActivity : BaseMvpActivity<SetoutAlcoholTestListPresenter>(), SetoutAlcoholTestListView {

    private lateinit var mAdapter: SetoutAlcoholTestAdapter

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
        mPresenter.getSetoutAlcoholTestList(PISUtil.getInstanceId(), PISUtil.getTokenKey())
    }

    private fun initView() {
        mHeaderBar.setTitleText("酒测列表")

        mCheckinRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mCheckinRv);

        mAdapter = SetoutAlcoholTestAdapter(this)
        mCheckinRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoutAlcoholTest> {
            override fun onItemClick(item: SetoutAlcoholTest, position: Int) {
                startActivity<SetoutAlcoholTestDetailActivity>("instanceId" to item.SetOutInstanceId, "taskId" to item.ID)
            }
        })
    }

    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<SetoutAlcoholTest>) {
        mAdapter.setData(result.toMutableList())

        if (result.isNotEmpty())
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        else
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }
}