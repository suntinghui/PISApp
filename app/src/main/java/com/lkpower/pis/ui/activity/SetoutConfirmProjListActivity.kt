package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.kennyc.view.MultiStateView
import com.lkpower.pis.R
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.protocol.SetoutConfirmProj
import com.lkpower.pis.ext.startLoading
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutGroupConfirmProjListPresenter
import com.lkpower.pis.presenter.view.SetoutConfirmProjListView
import com.lkpower.pis.ui.adapter.ConfirmProjAdapter
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.widgets.ImagePickerView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_setout_confirm_proj_list.*



/*
出乘管理-项目确认-列表-详情
 */
@Route(path = "/pis/SetoutConfirmProjListActivity")
class SetoutConfirmProjListActivity : BaseMvpActivity<SetoutGroupConfirmProjListPresenter>(), SetoutConfirmProjListView {

    private var GroupTaskId: String = ""
    private lateinit var mAdapter: ConfirmProjAdapter
    private lateinit var dataList: MutableList<SetoutConfirmProj>

    private lateinit var attMap:MutableMap<String, List<AttModel>>

    private var remark: String = "" // 暂存记录提交的内容

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_confirm_proj_list)

        GroupTaskId = intent.getStringExtra("GroupTaskId")
        dataList = mutableListOf()
        attMap = mutableMapOf()

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
                            getImagePickerView(position)?.uploadAction(dataList.get(position).ID, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
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

        mProjRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManager = recyclerView?.getLayoutManager()
                if (layoutManager is LinearLayoutManager) {
                    var first = layoutManager.findFirstVisibleItemPosition()
                    var last = layoutManager.findLastVisibleItemPosition()

                    for (position in first..last) {
                        // 这里有一个潜在的可能性bug，比如选定本地图片后在没有上传时滚动可能会将本地图片清除，需要用户再次进行选择
                        // 在setNetImages里加了一个与原有数据的比较操作，如果两次的list是相同的，则不刷新。这样能解决掉本地图片被刷没的现象。
                        getImagePickerView(position)?.setNetImages(attMap.get("${position}"))
                    }
                }

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

        ViewUtils.success(this, "提交成功")

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

        attMap.put("$currentIndex", result)

        // 找到项目后进行设置
        getImagePickerView(currentIndex)?.setNetImages(result)
    }


    // 根据给定的序号得到ImagePickerView
    private fun getImagePickerView(position: Int): ImagePickerView? {
        try {
            // 注意：因为RecyclerView的复用性，不可见的item为空，所以下面的layout肯定是null的。
            var layout: LinearLayout = mProjRv.layoutManager.findViewByPosition(position) as LinearLayout
            var imagePickerView = layout?.findViewById<ImagePickerView>(R.id.mPickerView)
            imagePickerView.setAttType(BaseConstant.Att_Type_Other)
            return imagePickerView
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
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
                    getImagePickerView(mAdapter.getCurrentIndex())?.onPickerDoneResult(selectList)
                }
            }
        }
    }


}