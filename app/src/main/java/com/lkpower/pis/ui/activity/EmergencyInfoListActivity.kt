package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.utils.PISUtil
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.EmergencyInfo
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.injection.component.DaggerEmergencyInfoComponent
import com.lkpower.pis.injection.module.EmergencyInfoModule
import com.lkpower.pis.presenter.EmergencyInfoListPresenter
import com.lkpower.pis.presenter.view.EmergencyInfoListView
import com.lkpower.pis.ui.adapter.EmergencyInfoAdapter
import com.lkpower.pis.utils.PageBeanUtil
import kotlinx.android.synthetic.main.activity_driving_info_list.*
import org.jetbrains.anko.startActivity


@Route(path = "/pis/EmergencyInfoListActivity")
class EmergencyInfoListActivity : BaseMvpActivity<EmergencyInfoListPresenter>(), EmergencyInfoListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private lateinit var mAdapter: EmergencyInfoAdapter

    private var mCurrentPage = 1
    private var mTotalPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_driving_info_list)

        initView()
        initRefreshLayout()
        loadData()
    }

    private fun initView() {
        mHeaderBar.setTitleText("应急列表")

        mDrivingRv.layoutManager = LinearLayoutManager(this)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mDrivingRv);

        mAdapter = EmergencyInfoAdapter(this)
        mDrivingRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<EmergencyInfo> {
            override fun onItemClick(item: EmergencyInfo, position: Int) {
                startActivity<EmergencyInfoDetailActivity>("ID" to item.EmInfoId)
            }
        })

    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getEmergencyInfoList("{}", PageBeanUtil.getPageBeanJson(mCurrentPage), PISUtil.getTokenKey())

    }

    override fun injectComponent() {
        DaggerEmergencyInfoComponent.builder().activityComponent(mActivityComponent).emergencyInfoModule(EmergencyInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: ListResult<EmergencyInfo>) {
        mTotalPage = result.PageInfo.TotalPage.toInt()

        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()

        if (result != null && result.PageInfo.TotalRecordSize.toInt() > 0) {
            if (mCurrentPage == 1) {
                mAdapter.setData(result.ListRecord.toMutableList())
            } else {
                mAdapter.dataList.addAll(result.ListRecord.toMutableList())
                mAdapter.notifyDataSetChanged()
            }

            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT

        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /*
        初始化刷新视图
     */
    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(this, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.common_bg)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.common_bg)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    override fun onDataIsNull() {
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        return if (mCurrentPage < mTotalPage) {
            mCurrentPage++
            loadData()
            true
        } else {
            false
        }
    }

    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        loadData()
    }
}