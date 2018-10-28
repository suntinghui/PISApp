package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.google.gson.Gson
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.ext.onClick
import com.lkpower.base.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.FaultInfo
import com.lkpower.pis.data.protocol.FaultInfoConfirm
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.injection.component.DaggerFaultInfoComponent
import com.lkpower.pis.injection.module.FaultInfoModule
import com.lkpower.pis.presenter.FaultInfoListPresenter
import com.lkpower.pis.presenter.view.FaultInfoListView
import com.lkpower.pis.ui.adapter.FaultInfoAdapter
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.PageBeanUtil
import kotlinx.android.synthetic.main.activity_fault_history_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/FaultHistoryListActivity")
class FaultHistoryListActivity : BaseMvpActivity<FaultInfoListPresenter>(), FaultInfoListView, BGARefreshLayout.BGARefreshLayoutDelegate  {

    private lateinit var mAdapter: FaultInfoAdapter

    private var mCurrentPage = 1
    private var mTotalPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fault_history_list)

        initView()

        initRefreshLayout()

        loadData()
    }

    private fun initView() {
        mHeaderBar.setTitleText("故障列表")

        mHistoryRv.layoutManager = LinearLayoutManager(this)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mHistoryRv);

        mAdapter = FaultInfoAdapter(this)
        mHistoryRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<FaultInfo> {
            override fun onItemClick(item: FaultInfo, position: Int) {
                startActivity<FaultHistoryConfirmActivity>("ID" to item.FaultId)
            }
        })

        mSearchBtn.onClick { loadData() }
    }

    private fun loadData() {
        mMultiStateView.startLoading()

        var faultConfirm = FaultInfoConfirm("", "", "", "", "", "", "")
        var fault = FaultInfo("", mTrainNoEt.text.toString(), "", "", "", "", "", "", "", "", PISUtil.getInstanceId(), faultConfirm)

        mPresenter.getFaultInfoList(Gson().toJson(fault), PageBeanUtil.getPageBeanJson(mCurrentPage), PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerFaultInfoComponent.builder().activityComponent(mActivityComponent).faultInfoModule(FaultInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: ListResult<FaultInfo>) {
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