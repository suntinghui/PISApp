package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.widget.LinearLayout
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.widgets.ImagePickerView
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.RiskItem
import com.lkpower.pis.data.protocol.TaskConveyDetail
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.ext.setVisible
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.TaskConveyDetailPresenter
import com.lkpower.pis.presenter.view.TaskConveyDetailView
import com.lkpower.pis.ui.adapter.RiskItemAdapter
import com.lkpower.pis.utils.ViewUtils.buttonEnable
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import info.hoang8f.widget.FButton
import kotlinx.android.synthetic.main.activity_taskconvey_detail.*
import java.lang.Exception


class TaskConveyDetailActivity : BaseMvpActivity<TaskConveyDetailPresenter>(), TaskConveyDetailView {

    private var ConveyDetailId: String = ""
    private lateinit var mAdapter: RiskItemAdapter
    private lateinit var mDetail: TaskConveyDetail

    private lateinit var dataList: MutableList<RiskItem>

    private lateinit var attMap:MutableMap<String, List<AttModel>>

    private var remark: String = "" // 暂存记录提交的内容

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_taskconvey_detail)

        ConveyDetailId = intent.getStringExtra("ConveyDetailId")
        dataList = mutableListOf()
        attMap = mutableMapOf()

        initView()

        loadDetail()
    }

    private fun initView() {

        mTaskConfirmLayout.setVisible(false)

        mTaskConfirmBtn.setButtonColor(getResources().getColor(R.color.fbutton_color_alizarin))
        mTaskConfirmBtn.isShadowEnabled = true
        mTaskConfirmBtn.shadowHeight = 5
        mTaskConfirmBtn.cornerRadius = 5
        mTaskConfirmBtn.onClick { sendConfrimAction() }


        mRiskItemsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = RiskItemAdapter(this)
        mRiskItemsRv.adapter = mAdapter

        mAdapter.setOnSubmitConfirmProjListener(object : RiskItemAdapter.SubmitConfirmProjListener {
            override fun onSubmit(position: Int, btn:FButton, feedback: String) {
                remark = feedback

                AlertView("提示", "已确认信息无误，立即提交？", "取消", arrayOf("确定"), null, this@TaskConveyDetailActivity, AlertView.Style.Alert, OnItemClickListener { o, index ->
                    when (index) {
                        0 -> {
                            buttonEnable(this@TaskConveyDetailActivity, btn, false)

                            this@TaskConveyDetailActivity.showLoading()
                            // 开始上传图片
                            getImagePickerView(position).uploadAction(dataList.get(position).ItemId, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
                        }
                    }
                }).show()
            }
        })

        // 图片上传成功后的事件，提交项目
        mAdapter.setOnUploadImageDoneListener(object : RiskItemAdapter.UploadImageDoneListener {
            override fun complete(position: Int) {
                mPresenter.taskRiskItemConfirm(mDetail.RiskItems.get(position).ItemId, remark, PISUtil.getTokenKey())
            }
        })

        mRiskItemsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        mPresenter.getTaskConveyDetail(ConveyDetailId, PISUtil.getTokenKey())

        mPresenter.getNoDoneRiskCount(ConveyDetailId, PISUtil.getTokenKey())
    }

    // 提交总任务
    private fun sendConfrimAction() {
        if (mTaskConfirmRemarEt.text.toString().isNullOrEmpty()) {
            ViewUtils.warning(this, "请输入意见")
            return
        }

        buttonEnable(this, mTaskConfirmBtn, false)
        mPresenter.taskConveyConfirm(ConveyDetailId, mTaskConfirmRemarEt.text.toString(), PISUtil.getTokenKey())
    }


    override fun onGetDetailResult(result: TaskConveyDetail) {
        try {
            this.mDetail = result

            mTitleView.setContentText(result.TaskTitle)
            mContentView.setContentText(result.TaskContent)
            mSetOutTypeNameView.setContentText(result.SetOutTypeName)
            mCompleteTimeView.setContentText(result.CompleteTime)
            mPublisherNameView.setContentText(result.PublisherName)
            mPublishDateView.setContentText(result.PublishDate)

            var riskItems = result.RiskItems

            if (riskItems.isNotEmpty()) {

                dataList = riskItems.toMutableList()

                riskItems.map {
                    // 获取每一个子项的图片
                    mPresenter.getAttList(it.ItemId, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
                }
                mAdapter.setData(riskItems.toMutableList())

            }

            mTaskConfirmRemarEt.text = Editable.Factory.getInstance().newEditable(result.ConfirmDetail.FeedBack)
            mTaskConfirmBtn.setVisible(result.ConfirmDetail.FeedBack.isNullOrEmpty())
            mTaskConfirmRemarEt.isEnabled = result.ConfirmDetail.FeedBack.isNullOrEmpty()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 项目提交成功后的响应
    override fun onConfirmRiskResult(result: Boolean) {
        this.hideLoading()

        ViewUtils.success(this, "提交成功")

        loadDetail()
    }



    // 查询未处理的风险项的数量,数量为0才显示该控件。并且有意见时说明已经提交过，不再显示提交按纽
    override fun onGetNoRiskCountResult(result: String) {
        try {
            var count = result.toInt()
            mTaskConfirmLayout.setVisible(count == 0)

        } catch (e: Exception) {
            e.printStackTrace()
            mTaskConfirmLayout.setVisible(false)
        }

    }

    // 处理总任务
    override fun onConfirmConveyResult(result: Boolean) {
        ViewUtils.success(this, "处理完成")

        loadDetail()
    }

    override fun confirmConveyComplete() {
        buttonEnable(this, mTaskConfirmBtn, true, R.color.fbutton_color_alizarin)
    }

    // 查询图片后的响应
    override fun onGetAttListResult(result: List<AttModel>) {
        if (result.isEmpty()) {
            return
        }

        // 根据查询到的图片搜索是归属于哪一个项目的内容
        var currentIndex = 0
        dataList.mapIndexed { index, riskItem ->
            if (riskItem.ItemId == result.first().BusId) {
                currentIndex = index
            }
        }

        // 找到项目后进行设置
        getImagePickerView(currentIndex)?.setNetImages(result)
    }


    // 根据给定的序号得到ImagePickerView
    private fun getImagePickerView(position: Int): ImagePickerView {
        var layout: LinearLayout = mRiskItemsRv.layoutManager.findViewByPosition(position) as LinearLayout
        var imagePickerView = layout?.findViewById<ImagePickerView>(R.id.mPickerView)
        imagePickerView.setAttType(BaseConstant.Att_Type_Other)
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