package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.PublishInfo
import com.lkpower.pis.ui.adapter.PublishAdapter
import kotlinx.android.synthetic.main.activity_publish_list.*
import org.jetbrains.anko.startActivity


@Route(path="/pis/PublishListActivity")
class PublishListActivity : BaseActivity() {

    private lateinit var mAdapter: PublishAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_publish_list)

        //ARouter注册
        ARouter.getInstance().inject(this)

        initView()

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
                startActivity<PublishDetailActivity>()
            }
        })

    }

    private fun loadData() {
        mAdapter.setData(mutableListOf(
                PublishInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                PublishInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                PublishInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                PublishInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                PublishInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                PublishInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                PublishInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                PublishInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                PublishInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53")
        ))
    }
}