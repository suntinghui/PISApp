package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.widgets.ImagePickerView
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
import com.lkpower.pis.utils.PISUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_setout_confirm_proj_list.*
import org.jetbrains.anko.toast

/*
出乘管理-项目确认-列表-详情
 */
class SetoutConfirmProjListActivity : BaseMvpActivity<SetoutGroupConfirmProjListPresenter>(), SetoutConfirmProjListView {

    private var GroupTaskId: String = ""
    private lateinit var mAdapter: ConfirmProjAdapter
    private lateinit var dataList: MutableList<SetoutConfirmProj>

    private var remark: String = "" // 暂存记录提交的内容

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_confirm_proj_list)

        GroupTaskId = intent.getStringExtra("GroupTaskId")
        dataList = mutableListOf()

        initView()

        loadDetail()
    }

    private fun initView() {
        mProjRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ConfirmProjAdapter(this)
        mProjRv.adapter = mAdapter

        // 每一部分的确认提交事件
        mAdapter.setOnSubmitConfirmProjListener(object : ConfirmProjAdapter.SubmitConfirmProjListener {
            override fun onSubmit(position: Int, content: String) {

                remark = content

                AlertView("提示", "已确认信息无误，立即提交？", "取消", arrayOf("确定"), null, this@SetoutConfirmProjListActivity, AlertView.Style.Alert, OnItemClickListener { o, index ->
                    when (index) {
                        0 -> {
                            this@SetoutConfirmProjListActivity.showLoading()
                            // 开始上传图片
                            getImagePickerView(position).uploadAction(dataList.get(position).ID, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
                        }
                    }
                }).show()
            }
        })

        // 图片上传成功后的事件，提交项目
        mAdapter.setOnUploadImageDoneListener(object : ConfirmProjAdapter.UploadImageDoneListener {
            override fun complete(position: Int) {
                mPresenter.setoutConfirmProj(dataList.get(position).ID, remark, PISUtil.getTokenKey())
            }
        })
    }

    // 加载详情
    private fun loadDetail() {
        mMultiStateView.startLoading()
        mPresenter.getSetOutConfirmProjList(PISUtil.getInstanceId(), GroupTaskId, PISUtil.getTokenKey())
    }

    // 已得到详情信息
    override fun onGetProjListResult(result: List<SetoutConfirmProj>) {
        if (result.isNotEmpty()) {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT

            dataList = result.toMutableList()

            result.map {
                // 获取每一个子项的图片
                mPresenter.getAttList(it.ID, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
            }
            mAdapter.setData(result.toMutableList())

        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    // 项目提交成功后的响应
    override fun onConfirmResult(result: Boolean) {
        this.hideLoading()

        toast("提交成功")

        loadDetail()
    }

    // 查询图片后的响应
    override fun onGetAttListResult(result: List<AttModel>) {
        if (result.isEmpty()) {
            return
        }

        // 根据查询到的图片搜索是归属于哪一个项目的内容
        var currentIndex = 0
        dataList.mapIndexed { index, setoutConfirmProj ->
            if (setoutConfirmProj.ID == result.first().BusId) {
                currentIndex = index
            }
        }

        // 找到项目后进行设置
        getImagePickerView(currentIndex).setNetImages(result)
    }


    // 根据给定的序号得到ImagePickerView
    private fun getImagePickerView(index: Int): ImagePickerView {
        var layout: LinearLayout = mProjRv.getChildAt(index) as LinearLayout
        var imagePickerView = layout.findViewById<ImagePickerView>(R.id.mPickerView)
        return imagePickerView
    }

    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    getImagePickerView(mAdapter.getCurrentIndex()).onPickerDoneResult(selectList)
                }
            }
        }
    }


}