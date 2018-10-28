package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.lkpower.pis.utils.PISUtil
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.data.protocol.AttModel
import com.lkpower.base.ext.startLoading
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutConfirmProj
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutGroupConfirmProjListPresenter
import com.lkpower.pis.presenter.view.SetoutConfirmProjListView
import com.lkpower.pis.ui.adapter.ConfirmProjAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_setout_alcoholtest_detail.*
import kotlinx.android.synthetic.main.activity_setout_confirm_proj_list.*
import org.jetbrains.anko.toast

/*
出乘管理-项目确认-列表-详情

 */
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

        mAdapter = ConfirmProjAdapter(this)
        mProjRv.adapter = mAdapter

        mAdapter.setOnSubmitConfirmProjListener(object : ConfirmProjAdapter.SubmitConfirmProjListener {
            override fun onSubmit(position: Int, content: String) {
                AlertView("提示", "已确认信息无误，立即提交？", "取消", arrayOf("确定"), null, this@SetoutConfirmProjListActivity, AlertView.Style.Alert, OnItemClickListener { o, index ->
                    when (index) {
                        0 -> {
                            mPresenter.setoutConfirmProj(taskIdList?.get(position), PISUtil.getTokenKey())
                        }
                    }
                }).show()
            }

        })
    }

    private fun loadDetail() {
        mMultiStateView.startLoading()
        mPresenter.getSetOutConfirmProjList(PISUtil.getInstanceId(), GroupTaskId, PISUtil.getTokenKey())
    }


    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetProjListResult(result: List<SetoutConfirmProj>) {
        taskIdList = result.map { it.ID }
        mAdapter.setData(result.toMutableList())

        if (result.isNotEmpty())
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        else
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
    }

    override fun onConfirmResult(result: Boolean) {
        toast("提交成功")

        loadDetail()
    }

    override fun onGetAttListResult(result: List<AttModel>) {
        mImagePicker.setNetImages(result)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    mImagePicker.onPickerDoneResult(selectList)
                }
            }
        }
    }


}