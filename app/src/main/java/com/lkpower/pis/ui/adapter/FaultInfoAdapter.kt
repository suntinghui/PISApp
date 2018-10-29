package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.ext.setVisible
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.FaultInfo
import kotlinx.android.synthetic.main.layout_fault_item.view.*
import kotlinx.android.synthetic.main.layout_publish_item.view.*

class FaultInfoAdapter(context: Context) : BaseRecyclerViewAdapter<FaultInfo, FaultInfoAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_fault_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mFailPartTv.text = model.PartName + " " + if (model.FaultTypeName.isNullOrEmpty()) "" else model.FaultTypeName
        holder.itemView.mRemarkTv.setVisible(model.Remark.isNullOrEmpty().not())
        holder.itemView.mRemarkTv.text = model.Remark
        holder.itemView.mReportTimeTv.text = model.ReportTime
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}