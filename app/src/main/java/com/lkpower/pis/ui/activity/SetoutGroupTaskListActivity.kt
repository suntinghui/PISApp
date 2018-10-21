package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutGroupTask
import com.lkpower.pis.injection.component.DaggerTasktanceComponent
import com.lkpower.pis.injection.module.TasktanceModule
import com.lkpower.pis.presenter.SetoutGroupTaskListPresenter
import com.lkpower.pis.presenter.view.SetoutGroupTaskListView
import com.lkpower.pis.ui.adapter.SetOutGroupTaskAdapter
import kotlinx.android.synthetic.main.activity_setout_grouptask_list.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/SetoutGroupTaskListActivity")
class SetoutGroupTaskListActivity : BaseMvpActivity<SetoutGroupTaskListPresenter>(), SetoutGroupTaskListView {

    private lateinit var mAdapter: SetOutGroupTaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_grouptask_list)

        initView()
    }

    private fun initView() {
        mHeaderBar.setTitleText("项目确认列表")

        mTaskRv.layoutManager = LinearLayoutManager(this)
        RecyclerViewDivider.with(this).build().addTo(mTaskRv);

        mAdapter = SetOutGroupTaskAdapter(this)
        mTaskRv.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<SetoutGroupTask> {
            override fun onItemClick(item: SetoutGroupTask, position: Int) {
                startActivity<SetoutConfirmProjListActivity>("GroupTaskId" to item.ID)
            }
        })
    }

    override fun injectComponent() {
        DaggerTasktanceComponent.builder().activityComponent(mActivityComponent).tasktanceModule(TasktanceModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetListResult(result: List<SetoutGroupTask>) {
        mAdapter.setData(result.toMutableList())
    }


}