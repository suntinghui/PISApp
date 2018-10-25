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
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.injection.component.DaggerLearnDocComponent
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.presenter.LearnDocListPresenter
import com.lkpower.pis.presenter.view.LearnDocListView
import com.lkpower.pis.ui.adapter.LearnDocAdapter
import com.lkpower.pis.utils.PageBeanUtil
import kotlinx.android.synthetic.main.activity_learndoc_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/LearnDocListActivity")
class LearnDocListActivity : BaseMvpActivity<LearnDocListPresenter>(), LearnDocListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private var mCurrentPage = 1
    private var mTotalPage = 1

    private lateinit var mAdapter: LearnDocAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_learndoc_list)

        initView()
        initRefreshLayout()
        loadData()
    }

    private fun initView() {
        mDocRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mDocRv);

        mAdapter = LearnDocAdapter(this)
        mDocRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<LearnDoc> {
            override fun onItemClick(item: LearnDoc, position: Int) {
                startActivity<LearnDocDetailActivity>("docId" to item.DocId)
            }
        })
    }

    override fun injectComponent() {
        DaggerLearnDocComponent.builder().activityComponent(mActivityComponent).learnDocModule(LearnDocModule()).build().inject(this)
        mPresenter.mView = this
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getSetoutCheckinList("{}", PageBeanUtil.getPageBeanJson(mCurrentPage), AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }

    override fun onGetListResult(result: ListResult<LearnDoc>) {
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