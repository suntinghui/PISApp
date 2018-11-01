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
import com.lkpower.base.utils.PISUtil
import com.lkpower.base.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.PublishInfo
import com.lkpower.pis.injection.component.DaggerPublishComponent
import com.lkpower.pis.injection.module.PublishModule
import com.lkpower.pis.presenter.PublishListPresenter
import com.lkpower.pis.presenter.view.PublishListView
import com.lkpower.pis.ui.adapter.PublishAdapter
import com.lkpower.pis.utils.PageBeanUtil
import kotlinx.android.synthetic.main.activity_publish_list.*
import org.jetbrains.anko.startActivity


@Route(path = "/pis/PublishListActivity")
class PublishListActivity : BaseMvpActivity<PublishListPresenter>(), PublishListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private lateinit var mAdapter: PublishAdapter

    private var mCurrentPage = 1
    private var mTotalPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_publish_list)

        initView()
        initRefreshLayout()
        loadData()
    }

    private fun initView() {
        mPublishRv.layoutManager = LinearLayoutManager(this)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mPublishRv);

        mAdapter = PublishAdapter(this)
        mPublishRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<PublishInfo> {
            override fun onItemClick(item: PublishInfo, position: Int) {
                startActivity<PublishDetailActivity>("ID" to item.ID)
            }
        })

    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getPublishInfoList("{}", PageBeanUtil.getPageBeanJson(mCurrentPage), PISUtil.getTokenKey())

    }

    override fun injectComponent() {
        DaggerPublishComponent.builder().activityComponent(mActivityComponent).publishModule(PublishModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: ListResult<PublishInfo>) {
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