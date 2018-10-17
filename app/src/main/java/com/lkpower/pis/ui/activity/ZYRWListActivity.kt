package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.StationInfo
import com.lkpower.pis.ui.adapter.StationAdapter
import kotlinx.android.synthetic.main.activity_zyrw_list.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Route(path="/pis/ZYRWListActivity")
class ZYRWListActivity : BaseActivity() {

    private lateinit var mAdapter: StationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_zyrw_list)

        //ARouter注册
        ARouter.getInstance().inject(this)

        initView()

        loadData()
    }

    private fun initView() {
        mStationRv.layoutManager = LinearLayoutManager(this)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mStationRv);

        mAdapter = StationAdapter(this)
        mStationRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<StationInfo> {
            override fun onItemClick(item: StationInfo, position: Int) {
                startActivity<ZYRWTaskInfoActivity>()
            }
        })

    }

    private fun loadData() {
        mAdapter.setData(mutableListOf(StationInfo("北京站"), StationInfo("北京站"), StationInfo("北京站"), StationInfo("北京站"), StationInfo("北京站")))
    }
}