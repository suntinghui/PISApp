package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.XJ_CZSL
import com.lkpower.pis.injection.component.DaggerInspectionComponent
import com.lkpower.pis.injection.module.InspectionModule
import com.lkpower.pis.presenter.InspectionStationListPresenter
import com.lkpower.pis.presenter.view.InspectionStationListView
import com.lkpower.pis.ui.adapter.InspectionStationAdapter
import kotlinx.android.synthetic.main.activity_inspection_station_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/InspectionStationListActivity")
class InspectionStationListActivity : BaseMvpActivity<InspectionStationListPresenter>(), InspectionStationListView {

    private lateinit var mAdapter: InspectionStationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_inspection_station_list)

        initView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun initView() {
        mStationRv.layoutManager = LinearLayoutManager(this)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mStationRv);

        mAdapter = InspectionStationAdapter(this)
        mStationRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<XJ_CZSL> {
            override fun onItemClick(item: XJ_CZSL, position: Int) {
                startActivity<InspectionTaskListActivity>("SiteId" to item.stationId)
            }
        })
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getSiteList(AppPrefsUtils.getString(BaseConstant.kInstanceId), AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }

    override fun injectComponent() {
        DaggerInspectionComponent.builder().activityComponent(mActivityComponent).inspectionModule(InspectionModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<XJ_CZSL>) {
        mAdapter.setData(result.toMutableList())
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
    }

    override fun onDataIsNull() {
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }
}