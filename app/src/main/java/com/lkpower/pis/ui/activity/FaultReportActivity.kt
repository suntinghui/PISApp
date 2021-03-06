package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.google.gson.Gson
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.utils.DateUtils
import com.lkpower.pis.widgets.ImagePickerView
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.FaultInfo
import com.lkpower.pis.data.protocol.FaultInfoConfirm
import com.lkpower.pis.data.protocol.SysDic
import com.lkpower.pis.ext.isNullOrEmpty
import com.lkpower.pis.injection.component.DaggerFaultInfoComponent
import com.lkpower.pis.injection.module.FaultInfoModule
import com.lkpower.pis.presenter.FaultInfoAddPresenter
import com.lkpower.pis.presenter.view.FaultInfoAddView
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils.buttonEnable
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import kotlinx.android.synthetic.main.activity_fault_report.*
import kotlinx.android.synthetic.main.activity_setoff_detail.*
import org.jetbrains.anko.toast
import java.lang.Exception

@Route(path = "/pis/FaultReportActivity")
class FaultReportActivity : BaseMvpActivity<FaultInfoAddPresenter>(), FaultInfoAddView {

    private lateinit var mFailPartAdapter: ArrayAdapter<String>
    private lateinit var failPartList: MutableList<SysDic>
    private lateinit var faultTypeList: MutableList<SysDic>
    private lateinit var checkTypeList: MutableList<SysDic>

    private lateinit var selectFailPart: SysDic
    private lateinit var selectFaultType: SysDic
    private lateinit var selectCheckType: SysDic

    private lateinit var uuid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fault_report)

        uuid = PISUtil.getUUID()

        initView()
    }

    private fun initView() {
        failPartList = mutableListOf<SysDic>()
        faultTypeList = mutableListOf<SysDic>()
        checkTypeList = mutableListOf<SysDic>()

        // 设置输入监听
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
            ViewUtils.closeKeyboard(this@FaultReportActivity)
            selectFailPart = failPartList.get(position)
            queryFaultTypeData()
        }


        // 设置选择事件
        mCheckTypeTv.onClick { queryCheckType() }

        mFaultTypeTv.onClick { showFaultTypeEvent() }

        mSendBtn.isShadowEnabled = true
        mSendBtn.shadowHeight = 5
        mSendBtn.cornerRadius = 5
        mSendBtn.onClick { sendAction() }

        mImagePicker.setAttType(BaseConstant.Att_Type_Other)
        // 上传图片的事件
        mImagePicker.setOnUploadListener(object : ImagePickerView.OnUploadListener {
            override fun onError() {
                buttonEnable(this@FaultReportActivity, mSendBtn, true)
                this@FaultReportActivity.hideLoading()
                ViewUtils.showSimpleAlert(this@FaultReportActivity, "有图片上传失败，请重新确定上传")
            }

            override fun onComplete() {
                buttonEnable(this@FaultReportActivity, mSendBtn, true)

                var faultConfirm = FaultInfoConfirm("", "", "", "", "", "", "")
                var fault = FaultInfo(uuid, mTrainNoEt.text.toString(), selectFailPart.ID, selectFailPart.DicValue, selectFaultType.ID, selectFaultType.DicValue, mRemarkEt.text.toString(), selectCheckType.ID, selectCheckType.DicValue, "", DateUtils.getNow("yyyy-MM-dd HH:mm:ss"), "", PISUtil.getInstanceId(), faultConfirm)
                mPresenter.addFaultInfo(PISUtil.getTokenKey(), Gson().toJson(fault))
            }

        })
    }

    // 发送事件
    private fun sendAction() {
        try {
            if (selectFailPart == null) {
                ViewUtils.warning(this, "请填写故障配件")
                return
            }

            // 有可能没有故障件下没有故障类型
            if (selectFaultType == null) {
                ViewUtils.warning(this, "没有选择故障类型")
                return
            }

            if (this::selectCheckType.isInitialized.not() || selectCheckType == null) {
                ViewUtils.warning(this, "没有选择检修类型")
                return
            }

            if (mTrainNoEt.text.toString().isNullOrEmpty()) {
                ViewUtils.warning(this, "请输入车号")
                return
            }

            if (mRemarkEt.text.toString().isNullOrEmpty()) {
                ViewUtils.warning(this, "请输入故障说明信息")
                return
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ViewUtils.warning(this, "请先填写相应信息")
            return
        }

        AlertView("提示", "您确定要提交吗?", "取消", arrayOf("确定"), null, this@FaultReportActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
            when (position) {
                0 -> {
                    buttonEnable(this, mSendBtn, false)
                    this@FaultReportActivity.showLoading()
                    mImagePicker.uploadAction(uuid, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
                }
            }
        }).show();
    }

    // 查询故障配件
    private fun queryFailListData(keyword: String) {
        mPresenter.getFailPartList(keyword)
    }

    // 查询故障类型
    private fun queryFaultTypeData() {
        if (selectFailPart == null) {
            ViewUtils.warning(this, "您所选择的故障配件无效")
            return
        }

        mPresenter.getFaultTypeList(selectFailPart.ID)
    }

    private fun queryCheckType() {
        mPresenter.getCheckTypeList()
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
        if (this::selectFailPart.isInitialized.not() || selectFailPart == null) {
            ViewUtils.warning(this, "请先填写故障配件")
            return
        }

        if (faultTypeList.isEmpty()) {
            ViewUtils.warning(this, "没有查询到故障类型")
            return
        }

        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            selectFaultType = faultTypeList.get(options1)
            mFaultTypeTv.text = selectFaultType.DicValue
        }
        ).build<String>()
        pickerView.setPicker(faultTypeList.map { it.DicValue })

        var index = faultTypeList.map { it.DicValue }.indexOf(mFaultTypeTv.text.toString())
        pickerView.setSelectOptions(if (index < 0) 0 else index)

        pickerView.show()
    }

    // 检修类型
    override fun onCheckTypeResult(result: List<SysDic>) {
        checkTypeList = result.toMutableList()

        try {
            if (checkTypeList == null || checkTypeList.isEmpty()) {
                ViewUtils.warning(this, "没有检修类型可供选择")
                return
            }

            var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
                selectCheckType = checkTypeList.get(options1)
                mCheckTypeTv.text = selectCheckType.DicValue
            }
            ).build<String>()
            pickerView.setPicker(checkTypeList.map { it.DicValue })
            var index = checkTypeList.map { it.DicValue }.indexOf(mCheckTypeTv.text)
            pickerView.setSelectOptions(if (index < 0) 0 else index)
            pickerView.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun injectComponent() {
        DaggerFaultInfoComponent.builder().activityComponent(mActivityComponent).faultInfoModule(FaultInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onAddDetailResult(result: CommonReturn) {
        this.hideLoading()
        ViewUtils.success(this, "上报成功")
        finish()
    }

    private fun refreshFailPart() {
        mFailPartAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, failPartList.map { it.DicValue })
        mFailPartTv.setAdapter(mFailPartAdapter)
        mFailPartAdapter.notifyDataSetChanged()
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