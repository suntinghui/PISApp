package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.google.gson.Gson
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.ext.onClick
import com.lkpower.base.ext.startLoading
import com.lkpower.base.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.FaultInfo
import com.lkpower.pis.data.protocol.FaultInfoConfirm
import com.lkpower.pis.data.protocol.ListResult
import com.lkpower.pis.data.protocol.SysDic
import com.lkpower.pis.injection.component.DaggerFaultInfoComponent
import com.lkpower.pis.injection.module.FaultInfoModule
import com.lkpower.pis.presenter.FaultInfoListPresenter
import com.lkpower.pis.presenter.view.FaultInfoListView
import com.lkpower.pis.ui.adapter.FaultInfoAdapter
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.PageBeanUtil
import kotlinx.android.synthetic.main.activity_fault_history_list.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.lang.Exception

@Route(path = "/pis/FaultHistoryListActivity")
class FaultHistoryListActivity : BaseMvpActivity<FaultInfoListPresenter>(), FaultInfoListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private lateinit var mFailPartAdapter: ArrayAdapter<String> // 输入提示的adapter
    private lateinit var failPartList: MutableList<SysDic>
    private lateinit var faultTypeList: MutableList<SysDic>

    private lateinit var selectFailPart: SysDic
    private lateinit var selectFaultType: SysDic

    private lateinit var mAdapter: FaultInfoAdapter // 列表的adapter

    private var mCurrentPage = 1
    private var mTotalPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fault_history_list)

        failPartList = mutableListOf<SysDic>()
        faultTypeList = mutableListOf<SysDic>()

        initView()

        initRefreshLayout()

        loadData()
    }

    private fun initView() {
        mHeaderBar.setTitleText("故障列表")

        // 设置故障件输入监听
        mFailPartTv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    failPartList.clear()
                    refreshFailPart()
                } else {
                    queryFailListData(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })

        // 设置选择事件
        mFailPartTv.setOnItemClickListener { parent, view, position, id ->
            ViewUtils.closeKeyboard(this@FaultHistoryListActivity)
            selectFailPart = failPartList.get(position)
            queryFaultTypeData()
        }

        mFaultTypeTv.onClick { showFaultTypeEvent() }

        mHistoryRv.layoutManager = LinearLayoutManager(this)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mHistoryRv);

        mAdapter = FaultInfoAdapter(this)
        mHistoryRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<FaultInfo> {
            override fun onItemClick(item: FaultInfo, position: Int) {
                startActivity<FaultHistoryConfirmActivity>("Id" to item.FaultId)
            }
        })

        mSearchBtn.onClick { loadData() }
    }

    private fun loadData() {
        mMultiStateView.startLoading()

        var trainNo = mTrainNoEt.text.toString()
        var PartId = ""
        var FaultType = ""

        try {
            PartId = selectFailPart.ID
        } catch (e: Exception) {
            PartId = ""
        }

        try {
            FaultType = selectFaultType.ID
        } catch (e: Exception) {
            FaultType = ""
        }

        var searchInfo = "{'TrainNo':$trainNo,'PartId':$PartId,'FaultType':$FaultType}"

        mPresenter.getFaultInfoList(searchInfo, PageBeanUtil.getPageBeanJson(mCurrentPage), PISUtil.getTokenKey())
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

    // 查询故障配件
    private fun queryFailListData(keyword: String) {
        mPresenter.getFailPartList(keyword)
    }

    // 查询故障类型
    private fun queryFaultTypeData() {
        if (selectFailPart == null) {
            toast("您所选择的故障配件无效")
            return
        }

        mPresenter.getFaultTypeList(selectFailPart.ID)
    }

    override fun onFailPartResult(result: List<SysDic>) {
        failPartList = result.toMutableList()
        refreshFailPart()
    }

    override fun onFaultTypeResult(result: List<SysDic>) {
        faultTypeList = result.toMutableList()
        if (faultTypeList.isNotEmpty()) {
            selectFaultType = faultTypeList.first()
            mFaultTypeTv.text = selectFaultType.DicValue
        } else {
            selectFaultType = SysDic("", "", "", "", "", "")
            mFaultTypeTv.text = "该故障件没有故障类型"
        }
    }

    // 选择故障类型
    private fun showFaultTypeEvent() {
        if (selectFailPart == null) {
            toast("请先填写故障配件")
            return
        }

        if (faultTypeList.isEmpty()) {
            toast("没有查询到故障类型")
            return
        }

        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            selectFaultType = faultTypeList.get(options1)
            mFaultTypeTv.text = selectFaultType.DicValue
        }
        ).build<String>()
        pickerView.setPicker(faultTypeList.map { it.DicValue })
        pickerView.setSelectOptions(faultTypeList.indexOf(faultTypeList.first { it.DicValue == mFaultTypeTv.text.toString() }))
        pickerView.show()
    }

    private fun refreshFailPart() {
        mFailPartAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, failPartList.map { it.DicValue })
        mFailPartTv.setAdapter(mFailPartAdapter)
        mFailPartAdapter.notifyDataSetChanged()
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