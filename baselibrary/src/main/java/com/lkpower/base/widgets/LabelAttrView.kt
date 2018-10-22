package com.kotlin.base.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.lkpower.base.R
import com.lkpower.base.ext.onClick
import com.lkpower.base.utils.DownloadFileUtils
import kotlinx.android.synthetic.main.layout_label_textview.view.*


/*
 * LabelAttrView封装
 */
class LabelAttrView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private var mLabelText: CharSequence? = null
    private var mContentText: CharSequence? = null

    //初始化属性
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelText)
        this.mLabelText = typedArray.getText(R.styleable.LabelText_labelText)
        this.mContentText = typedArray.getText(R.styleable.LabelText_contentText)
        initView()
        typedArray.recycle()
    }

    //初始化视图
    private fun initView() {
        View.inflate(context, R.layout.layout_label_attr_view, this)

        mLabelText?.let {
            mLabelTv.text = it
        }

        mContentText?.let {
            mContentTv.text = it
        }

    }

    fun setLabelText(label: String) {
        mLabelTv.text = label
    }

    fun setAttr(fileName:String, path: String) {
        mContentTv.text = fileName
        mContentTv.onClick { DownloadFileUtils.downloadAndOpenFile(context, fileName, path) }
    }

    fun getLayout(): LinearLayout {
        return mLayout
    }
}
