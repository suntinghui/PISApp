package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutConfirmProj
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutGroupConfirmProjListPresenter
import com.lkpower.pis.presenter.view.SetoutConfirmProjListView
import com.lkpower.pis.ui.adapter.ConfirmProjAdapter
import kotlinx.android.synthetic.main.activity_setout_confirm_proj_list.*
import org.jetbrains.anko.toast

class SetoutConfirmProjListActivity : BaseMvpActivity<SetoutGroupConfirmProjListPresenter>(), SetoutConfirmProjListView {

    private var GroupTaskId: String = ""
    private lateinit var mAdapter: ConfirmProjAdapter
    private lateinit var taskIdList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_confirm_proj_list)

        GroupTaskId = intent.getStringExtra("GroupTaskId")

        initView()

        loadDetail()
    }

    private fun initView() {
        mProjRv.layoutManager = LinearLayoutManager(this)
        // 设置分隔线
        RecyclerViewDivider.with(this).build().addTo(mProjRv);

        mAdapter = ConfirmProjAdapter(this)
        mProjRv.adapter = mAdapter

        mAdapter.setOnSubmitConfirmProjListener(object : ConfirmProjAdapter.SubmitConfirmProjListener {
            override fun onSubmit(position: Int, content: String) {
                AlertView("提示", "已确认信息无误，立即提交？", "取消", arrayOf("确定"), null, this@SetoutConfirmProjListActivity, AlertView.Style.Alert, OnItemClickListener { o, index ->
                    when (index) {
                        0 -> {
                            mPresenter.setoutConfirmProj(taskIdList?.get(position), AppPrefsUtils.getString(BaseConstant.kTokenKey))
                        }
                    }
                }).show()
            }

        })
    }

    private fun loadDetail() {
        mPresenter.getSetOutConfirmProjList(AppPrefsUtils.getString(BaseConstant.kInstanceId), GroupTaskId, AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }


    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetProjListResult(result: List<SetoutConfirmProj>) {
        taskIdList = result.map { it.ID }
        mAdapter.setData(result.toMutableList())
    }

    override fun onConfirmResult(result: String) {
        toast("提交成功")

        loadDetail()
    }


}