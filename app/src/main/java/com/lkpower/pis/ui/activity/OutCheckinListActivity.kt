package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.OutCheckInInfo
import com.lkpower.pis.injection.component.DaggerTasktanceComponent
import com.lkpower.pis.injection.component.DaggerUserComponent
import com.lkpower.pis.injection.module.TasktanceModule
import com.lkpower.pis.injection.module.UserModule
import com.lkpower.pis.presenter.GetOutCheckInListPresenter
import com.lkpower.pis.presenter.view.OutCheckinListView
import com.lkpower.pis.ui.adapter.OutCheckinAdapter
import kotlinx.android.synthetic.main.activity_out_checkin_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/OutCheckinListActivity")
class OutCheckinListActivity : BaseMvpActivity<GetOutCheckInListPresenter>(), OutCheckinListView {

    private lateinit var mAdapter: OutCheckinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_out_checkin_list)

        initView()
    }

    private fun initView() {
        mCheckinRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mCheckinRv);

        mAdapter = OutCheckinAdapter(this)
        mCheckinRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<OutCheckInInfo> {
            override fun onItemClick(item: OutCheckInInfo, position: Int) {
                startActivity<OutCheckinDetailActivity>("taskId" to item.ID)
            }
        })
    }

    override fun injectComponent() {
        DaggerTasktanceComponent.builder().activityComponent(mActivityComponent).tasktanceModule(TasktanceModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<OutCheckInInfo>) {
        mAdapter.setData(result.toMutableList())
    }
}