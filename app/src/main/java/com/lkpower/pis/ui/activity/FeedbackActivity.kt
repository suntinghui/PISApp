package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.google.gson.Gson
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.utils.DateUtils
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.CommonReturn
import com.lkpower.pis.data.protocol.Feedback
import com.lkpower.pis.injection.component.DaggerSettingComponent
import com.lkpower.pis.injection.module.SettingModule
import com.lkpower.pis.presenter.FeedbackPresenter
import com.lkpower.pis.presenter.view.FeedbackView
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.utils.ViewUtils.buttonEnable
import kotlinx.android.synthetic.main.activity_feedback.*
import org.jetbrains.anko.toast

class FeedbackActivity : BaseMvpActivity<FeedbackPresenter>(), FeedbackView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feedback)

        initView()
    }

    private fun initView() {
        mSendBtn.isShadowEnabled = true
        mSendBtn.shadowHeight = 5
        mSendBtn.cornerRadius = 5
        mSendBtn.onClick { sendAction() }
    }

    private fun sendAction() {
        if (mContentEt.text.isNullOrEmpty()) {
            ViewUtils.warning(this, "意见内容不能为空")
            return
        }

        if (mContentEt.text.toString().length < 10) {
            ViewUtils.warning(this, "意见内容不能少于10个字")
            return
        }

        this.showLoading()

        var feedback = Feedback("", "", "", "", "", DateUtils.getNow("yyyy-MM-dd HH:mm:ss"), "", mContentEt.text.toString(), PISUtil.getInstanceId(), "", "")

        mPresenter.addFeebback(Gson().toJson(feedback), PISUtil.getTokenKey())
    }

    override fun injectComponent() {
        DaggerSettingComponent.builder().activityComponent(mActivityComponent).settingModule(SettingModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onAddResult(result: CommonReturn) {
        this.hideLoading()
        ViewUtils.success(this, "提交成功，感谢您的反馈")
        finish()
    }
}