package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.onClick
import com.lkpower.base.ext.setVisible
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.SetoutAlcoholTest
import com.lkpower.pis.injection.component.DaggerSetoutComponent
import com.lkpower.pis.injection.module.SetoutModule
import com.lkpower.pis.presenter.SetoutAlcoholTestDetailPresenter
import com.lkpower.pis.presenter.view.SetoutAlcoholTestDetailView
import kotlinx.android.synthetic.main.activity_setout_alcoholtest_detail.*
import org.jetbrains.anko.toast

class SetoutAlcoholTestDetailActivity : BaseMvpActivity<SetoutAlcoholTestDetailPresenter>(), SetoutAlcoholTestDetailView {

    // 0不通过 1通过
    val RESULT_LIST = listOf<String>("酒测不通过", "酒测通过")

    private lateinit var instanceId: String
    private lateinit var taskId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setout_alcoholtest_detail)

        instanceId = intent.getStringExtra("instanceId")
        taskId = intent.getStringExtra("taskId")

        this.initView()

        queryDetail()
    }

    private fun initView() {
        mResultTv.text = RESULT_LIST.get(0)
        mResultTv.onClick { this.showResultEvent() }

        mOperBtn.isShadowEnabled = true
        mOperBtn.shadowHeight = 5
        mOperBtn.cornerRadius = 5
        mOperBtn.onClick { setAction() }
    }

    private fun queryDetail() {
        mPresenter.getSetoutAlcoholTestDetail(instanceId, taskId, AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }

    private fun setAction() {
        mPresenter.setOutAlcoholTest(taskId, RESULT_LIST.indexOf(mResultTv.text).toString(), AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }

    override fun injectComponent() {
        DaggerSetoutComponent.builder().activityComponent(mActivityComponent).setoutModule(SetoutModule()).build().inject(this)
        mPresenter.mView = this
    }

    // 选择车型
    private fun showResultEvent() {
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mResultTv.text = RESULT_LIST.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(RESULT_LIST)
        pickerView.setSelectOptions(RESULT_LIST.indexOf(mResultTv.text))
        pickerView.show()
    }

    // 取得详情
    override fun onGetDetailResult(item: SetoutAlcoholTest) {
        mClassNameView.setContentText(item.ClassName)
        mSendTimeView.setContentText(item.SendTime)
        mSiteNameView.setContentText(item.SiteName)
        mTaskStatusView.setContentText(if (item.TaskStatus == "0") "待执行" else "已完成")
        mLatesttAlcoholTestTimeView.setContentText(item.LatesttAlcoholTestTime)
        mBeginTimeView.setContentText(item.BeginTime)
        mTaskObjView.setContentText(item.TaskObj)

        // 任务状态:0=待执行，1=执行中，2=执行完成
        mShowTestLayout.setVisible(item.TaskStatus == "0")
    }

    // 报到
    override fun setOutResult(result: String) {
        toast("酒测成功")
        queryDetail()
    }
}