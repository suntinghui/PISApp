package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.MissionStateInfo
import com.lkpower.pis.utils.PISUtil
import kotlinx.android.synthetic.main.layout_inspection_task_item.view.*

class InspectionTaskAdapter(context: Context) : BaseRecyclerViewAdapter<MissionStateInfo, InspectionTaskAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_inspection_task_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        holder.itemView.mTitleTv.text = model.MissionName
        holder.itemView.mContentTv.text = model.MissionRemark
        holder.itemView.mStateTv.text = PISUtil.getInspectionTaskState(model.state)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}