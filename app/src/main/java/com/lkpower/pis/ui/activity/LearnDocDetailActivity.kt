package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.injection.component.DaggerLearnDocComponent
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.presenter.LearnDocDetailPresenter
import com.lkpower.pis.presenter.view.LearnDocDetailView
import org.jetbrains.anko.toast

class LearnDocDetailActivity : BaseMvpActivity<LearnDocDetailPresenter>(), LearnDocDetailView {

    private lateinit var docId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_learn_detail)

        docId = intent.getStringExtra("docId")

        initView()

        loadData()
    }

    private fun initView() {

    }

    private fun loadData() {
        mPresenter.getLearnDocModel(docId, AppPrefsUtils.getString(BaseConstant.kTokenKey))
    }


    override fun injectComponent() {
        DaggerLearnDocComponent.builder().activityComponent(mActivityComponent).learnDocModule(LearnDocModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetDetailResult(result: LearnDoc) {

    }

    override fun setOutResult(result: String) {
        toast("操作成功")

    }


}