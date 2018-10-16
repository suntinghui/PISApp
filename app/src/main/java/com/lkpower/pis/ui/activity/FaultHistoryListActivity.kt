package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.ext.onClick
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.FaultInfo
import com.lkpower.pis.ui.adapter.FaultInfoAdapter
import kotlinx.android.synthetic.main.activity_fault_history_list.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Route(path = "/pis/FaultHistoryListActivity")
class FaultHistoryListActivity : BaseActivity() {

    private lateinit var mAdapter: FaultInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fault_history_list)

        ARouter.getInstance().inject(this)

        initView()

        loadData()
    }

    private fun initView() {

        mTypeTv.onClick { showTypeEvent() }
        mModelTv.onClick { showModelEvent() }

        mSearchBtn.setButtonColor(getResources().getColor(R.color.fbutton_color_carrot));
        mSearchBtn.setShadowEnabled(false);
        mSearchBtn.onClick { searchAction() }

        mHistoryRv.layoutManager = LinearLayoutManager(this)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mHistoryRv);

        mAdapter = FaultInfoAdapter(this)
        mHistoryRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<FaultInfo> {
            override fun onItemClick(item: FaultInfo, position: Int) {
                startActivity<FaultHistoryConfirmActivity>()
            }
        })

    }

    // 选择车型
    private fun showTypeEvent() {
        var list: List<String> = listOf("item1", "item2", "item3")
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mTypeTv.text = list.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(list)
        pickerView.setSelectOptions(list.indexOf(mTypeTv.text))
        pickerView.show()
    }

    // 选择车种
    private fun showModelEvent() {
        var list: List<String> = listOf("item1", "item2", "item3")
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mModelTv.text = list.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(list)
        pickerView.setSelectOptions(list.indexOf(mModelTv.text))
        pickerView.show()
    }

    private fun searchAction() {
        toast("=====")
    }

    private fun loadData() {
        mAdapter.setData(mutableListOf(
                FaultInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                FaultInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                FaultInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                FaultInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                FaultInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                FaultInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                FaultInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                FaultInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53"),
                FaultInfo("全体工作人员","应急指挥手机到站不能正常使用的请编行车信息发到指挥中心","2018-10-13 19:2:53")
        ))
    }
}