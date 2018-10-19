package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutCheckInInfo
import com.lkpower.pis.data.protocol.SetoutInfo
import com.lkpower.pis.injection.component.DaggerTasktanceComponent
import com.lkpower.pis.injection.module.TasktanceModule
import com.lkpower.pis.presenter.SetoutCheckInListPresenter
import com.lkpower.pis.presenter.SetoutListPresenter
import com.lkpower.pis.presenter.view.SetoutCheckinListView
import com.lkpower.pis.presenter.view.SetoutListView
import com.lkpower.pis.ui.adapter.SetOutAdapter
import com.lkpower.pis.ui.adapter.SetOutCheckinAdapter
import kotlinx.android.synthetic.main.activity_setout_checkin_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/SetoutListActivity")
class SetoutListActivity : BaseMvpActivity<SetoutListPresenter>(), SetoutListView {

    private lateinit var mAdapter: SetOutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_checkin_list)

        initView()

        queryList()
    }

    private fun initView() {
        mHeaderBar.setTitleText("出乘列表")

        mCheckinRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mCheckinRv);

        mAdapter = SetOutAdapter(this)
        mCheckinRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoutInfo> {
            override fun onItemClick(item: SetoutInfo, position: Int) {
                startActivity<SetoutDetailActivity>("taskId" to item.SetOutInstanceId)
            }
        })
    }

    private fun queryList() {
        mPresenter.getSetoutList(AppPrefsUtils.getString(BaseConstant.kInstanceId), AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }

    override fun injectComponent() {
        DaggerTasktanceComponent.builder().activityComponent(mActivityComponent).tasktanceModule(TasktanceModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<SetoutInfo>) {
        mAdapter.setData(result.toMutableList())
    }
}