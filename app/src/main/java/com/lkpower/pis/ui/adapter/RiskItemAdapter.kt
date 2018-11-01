package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.widgets.ImagePickerView
import com.lkpower.base.common.BaseApplication.Companion.context
import com.lkpower.base.ext.onClick
import com.lkpower.base.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.RiskItem
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.layout_risk_item.view.*

class RiskItemAdapter(context: Context) : BaseRecyclerViewAdapter<RiskItem, RiskItemAdapter.ViewHolder>(context) {

    private lateinit var submitConfirmProjListener: SubmitConfirmProjListener
    private lateinit var uploadImageDoneListener: UploadImageDoneListener

    private var currentIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_risk_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mProjectNameTv.text = model.RiskDicName
        try {
            holder.itemView.mContentEt.text = Editable.Factory.getInstance().newEditable(model.FeedBackContent)
        } catch (e: Exception) {

        }
        holder.itemView.mConfirmBtn.onClick {
            if (holder.itemView.mContentEt.text.isNullOrEmpty()) {
                ViewUtils.warning(context, "请输入内容")
                return@onClick
            }

            submitConfirmProjListener?.onSubmit(position, holder.itemView.mContentEt.text.toString())
        }

        holder.itemView.mPickerView.setOnImageClickListener(object : ImagePickerView.OnImageClickLisenter {
            override fun onClick(path: String?) {
                // 为空则说明点击的是选择
                if (path.isNullOrEmpty()) {
                    currentIndex = position
                    Logger.e(currentIndex.toString())
                }
            }
        })

        holder.itemView.mPickerView.setOnUploadListener(object : ImagePickerView.OnUploadListener {
            override fun onError() {
                ViewUtils.showSimpleAlert(context, "有图片上传失败，请重新确定上传")
            }

            override fun onComplete() {
                this@RiskItemAdapter.uploadImageDoneListener.complete(position)
            }

        })
    }

    fun setOnSubmitConfirmProjListener(listener: SubmitConfirmProjListener) {
        this.submitConfirmProjListener = listener
    }

    fun setOnUploadImageDoneListener(listener: UploadImageDoneListener) {
        uploadImageDoneListener = listener
    }

    fun getCurrentIndex(): Int {
        return currentIndex
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface SubmitConfirmProjListener {
        fun onSubmit(position: Int, content: String)
    }

    interface UploadImageDoneListener {
        fun complete(position: Int)
    }

}