package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutCheckIn
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutCheckInListPresenter
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.ui.adapter.SetOutCheckinAdapter
import kotlinx.android.synthetic.main.activity_setout_checkin_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/SetoutCheckinListActivity")
class SetoutCheckinListActivity : BaseMvpActivity<SetoutCheckInListPresenter>(), SetoutCheckinListView {

    private lateinit var mAdapter: SetOutCheckinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_checkin_list)

        initView()
    }

    private fun initView() {
        mHeaderBar.setTitleText("报到列表")

        mCheckinRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mCheckinRv);

        mAdapter = SetOutCheckinAdapter(this)
        mCheckinRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoutCheckIn> {
            override fun onItemClick(item: SetoutCheckIn, position: Int) {
                startActivity<SetoutCheckinDetailActivity>("instanceId" to item.SetOutInstanceId, "taskId" to item.ID)
            }
        })
    }

    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<SetoutCheckIn>) {
        mAdapter.setData(result.toMutableList())
    }
}