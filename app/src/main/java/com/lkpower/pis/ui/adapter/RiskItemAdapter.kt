package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.bigkoo.alertview.AlertView
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.common.BaseApplication.Companion.context
import com.lkpower.base.ext.onClick
import com.lkpower.base.ext.setVisible
import com.lkpower.base.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.RiskItem
import kotlinx.android.synthetic.main.layout_risk_item.view.*

class RiskItemAdapter(context: Context) : BaseRecyclerViewAdapter<RiskItem, RiskItemAdapter.ViewHolder>(context) {

    private lateinit var listener:SubmitRiskItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_risk_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mDescTv.text = model.RiskDicName
        // 是否需要反馈:是=1，否=0
        holder.itemView.mFeedbackLayout.setVisible(model.NeedFeedBack == "1")
        holder.itemView.mConfirmBtn.onClick{
            if (holder.itemView.mContentEt.text.isNullOrEmpty())
                Toast.makeText(context, "请输入内容", Toast.LENGTH_SHORT).show()

            listener?.onSubmit(position, holder.itemView.mContentEt.text.toString())
        }
    }

    fun setOnSubmitRiskItemListener(listener:SubmitRiskItemListener) {
        this.listener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface SubmitRiskItemListener {
        fun onSubmit(position: Int, feedBack:String)
    }

}