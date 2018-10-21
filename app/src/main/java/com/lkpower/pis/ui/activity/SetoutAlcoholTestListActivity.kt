package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutAlcoholTestInfo
import com.lkpower.pis.injection.component.DaggerTasktanceComponent
import com.lkpower.pis.injection.module.TasktanceModule
import com.lkpower.pis.presenter.SetoutAlcoholTestListPresenter
import com.lkpower.pis.presenter.view.SetoutAlcoholTestListView
import com.lkpower.pis.ui.adapter.SetOutAlcoholTestAdapter
import kotlinx.android.synthetic.main.activity_setout_checkin_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/SetoutAlcoholTestListActivity")
class SetoutAlcoholTestListActivity : BaseMvpActivity<SetoutAlcoholTestListPresenter>(), SetoutAlcoholTestListView {

    private lateinit var mAdapter: SetOutAlcoholTestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_checkin_list)

        initView()
    }

    private fun initView() {
        mHeaderBar.setTitleText("酒测列表")

        mCheckinRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mCheckinRv);

        mAdapter = SetOutAlcoholTestAdapter(this)
        mCheckinRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoutAlcoholTestInfo> {
            override fun onItemClick(item: SetoutAlcoholTestInfo, position: Int) {
                startActivity<SetoutAlcoholTestDetailActivity>("instanceId" to item.SetOutInstanceId, "taskId" to item.ID)
            }
        })
    }

    override fun injectComponent() {
        DaggerTasktanceComponent.builder().activityComponent(mActivityComponent).tasktanceModule(TasktanceModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<SetoutAlcoholTestInfo>) {
        mAdapter.setData(result.toMutableList())
    }
}