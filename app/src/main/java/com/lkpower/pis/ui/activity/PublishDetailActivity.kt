package com.lkpower.pis.ui.activity

import android.graphics.Color
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.ui.activity.BaseActivity
import com.lkpower.pis.R
import kotlinx.android.synthetic.main.activity_publish_detail.*
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.SpannableStringBuilder
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.pis.data.protocol.PublishInfo
import com.lkpower.pis.injection.component.DaggerPublishComponent
import com.lkpower.pis.injection.module.PublishModule
import com.lkpower.pis.presenter.PublishDetailPresenter
import com.lkpower.pis.presenter.view.PublishDetailView
import java.lang.Exception


class PublishDetailActivity : BaseMvpActivity<PublishDetailPresenter>(), PublishDetailView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_publish_detail)

        loadData()
    }

    private fun loadData() {
        mPresenter.gePublishModel(intent.getStringExtra("ID"), "", AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }

    override fun injectComponent() {
        DaggerPublishComponent.builder().activityComponent(mActivityComponent).publishModule(PublishModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetDetailResult(result: PublishInfo) {
        try {
            mPublishTitleTv.text = result.Title
            mDutyUserTv.text = result.DutyUser
            mContentTv.text = result.Content
            mSubmitTimeTv.text = result.SubmitTime

            val span = SpannableStringBuilder(mContentTv.text)
            span.setSpan(ForegroundColorSpan(Color.TRANSPARENT), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            mContentTv.text = span

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}