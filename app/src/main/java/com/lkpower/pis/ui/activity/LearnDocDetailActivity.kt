package com.lkpower.pis.ui.activity

import android.os.Bundle
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.lkpower.pis.utils.PISUtil
import com.kotlin.base.widgets.LabelAttrView
import com.kotlin.base.widgets.LabelTextView
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.data.protocol.AttModel
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.injection.component.DaggerLearnDocComponent
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.presenter.LearnDocDetailPresenter
import com.lkpower.pis.presenter.view.LearnDocDetailView
import kotlinx.android.synthetic.main.activity_learn_detail.*
import org.jetbrains.anko.toast

class LearnDocDetailActivity : BaseMvpActivity<LearnDocDetailPresenter>(), LearnDocDetailView {

    private lateinit var docId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_learn_detail)

        docId = intent.getStringExtra("docId")

        loadData()
    }

    private fun loadData() {
        mPresenter.getLearnDocModel(docId, PISUtil.getTokenKey())
        mPresenter.getAttList(docId, BaseConstant.Att_Type_Other, PISUtil.getTokenKey())
    }


    override fun injectComponent() {
        DaggerLearnDocComponent.builder().activityComponent(mActivityComponent).learnDocModule(LearnDocModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetDetailResult(result: LearnDoc) {
        mDetailLayout.addView(LabelTextView(this).setLabelAndContent("文件标题", result.DocTitle))
        mDetailLayout.addView(LabelTextView(this).setLabelAndContent("文件说明", result.DocRemark))
        mDetailLayout.addView(LabelTextView(this).setLabelAndContent("发布日期", result.PublishDate))
        mDetailLayout.addView(LabelTextView(this).setLabelAndContent("有效状态", if (result.Enable == "1") "有效" else "无效"))
        mDetailLayout.addView(LabelTextView(this).setLabelAndContent("阅读状态", if (result.ReadState == "0") "未读" else "已读"))

    }

    override fun onDataIsNull() {
        mAttLayout.addView(LabelTextView(this).setLabelAndContent("文件", "无文件"))

    }

    override fun onGetAttListResult(result: List<AttModel>) {
        result.mapIndexed { index, attModel ->
            mAttLayout.addView(LabelAttrView(this).setLabelText("文件${index + 1}").setAttr(attModel.AttName, attModel.DownLoadUrl))
        }
    }

    override fun setOutResult(result: String) {
        toast("操作成功")

    }


}