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


class PublishDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.activity_publish_detail)

        initView()
    }

    private fun initView() {
        val span = SpannableStringBuilder(mContentTv.text)
        span.setSpan(ForegroundColorSpan(Color.TRANSPARENT), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mContentTv.text = span

    }
}