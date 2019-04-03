package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.os.Handler
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.widgets.LabelAttrView
import com.lkpower.pis.widgets.LabelTextView
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.ext.setVisible
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.injection.component.DaggerLearnDocComponent
import com.lkpower.pis.injection.module.LearnDocModule
import com.lkpower.pis.presenter.LearnDocDetailPresenter
import com.lkpower.pis.presenter.view.LearnDocDetailView
import info.hoang8f.widget.FButton
import kotlinx.android.synthetic.main.activity_learn_detail.*
import org.jetbrains.anko.toast
import java.lang.Exception

class LearnDocDetailActivity : BaseMvpActivity<LearnDocDetailPresenter>(), LearnDocDetailView {

    private lateinit var docId: String

    private val mHandler: Handler = Handler()
    private var mCount = 60

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
        mDetailLayout.removeAllViews()

        mDetailLayout.addView(LabelTextView(this).setLabelAndContent("文件标题", result.DocTitle))
        mDetailLayout.addView(LabelTextView(this).setLabelAndContent("文件说明", result.DocRemark))
        mDetailLayout.addView(LabelTextView(this).setLabelAndContent("发布日期", result.PublishDate.replace("00:00:00", "")))
        mDetailLayout.addView(LabelTextView(this).setLabelAndContent("有效状态", if (result.Enable == "1") "有效" else "无效"))
        mDetailLayout.addView(LabelTextView(this).setLabelAndContent("阅读状态", if (result.ReadState == "0" || null == result.ReadState) "未读" else "已读"))

        try {
            mCount = result.LearnDocMinControl.toInt() * 60
        } catch (e: Exception) {
            mCount = 60
        }

        if (result.ReadState == "0" || null == result.ReadState) {
            updateOperBtn(false)
            mHandler.postDelayed(countDown, 0)

        } else {
            mOperBtn.setVisible(false)
        }
    }

    override fun onGetAttListResult(result: List<AttModel>) {
        mAttLayout.removeAllViews()

        if (result.isEmpty()) {
            mAttLayout.addView(LabelTextView(this).setLabelAndContent("文件", "无文件"))
            return
        }

        result.mapIndexed { index, attModel ->
            mAttLayout.addView(LabelAttrView(this).setLabelText("文件${index + 1}").setAttr(attModel.AttName, attModel.DownLoadUrl))
        }
    }

    override fun setReadResult(result: Boolean) {
        ViewUtils.success(this, "操作成功")

        loadData()
    }

    private fun updateOperBtn(enable: Boolean) {
        mOperBtn.setVisible(true)

        mOperBtn.isEnabled = enable

        if (enable) {
            mOperBtn.setButtonColor(getResources().getColor(info.hoang8f.fbutton.R.color.fbutton_default_shadow_color))
            mOperBtn.setText("学习完成")
            mOperBtn.onClick { mPresenter.setLearnDocRead(docId, PISUtil.getTokenKey()) }
        } else {
            mOperBtn.setButtonColor(getResources().getColor(info.hoang8f.fbutton.R.color.fbutton_color_silver))
        }

    }

    /*
        倒计时
     */
    private val countDown = object : Runnable {
        override fun run() {
            mOperBtn.text = "学习完成 ($mCount s)"

            if (mCount > 0) {
                mHandler.postDelayed(this, 1000)
            } else {
                updateOperBtn(true)
            }
            mCount--
        }
    }


}