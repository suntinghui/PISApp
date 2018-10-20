package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.PublishInfo
import com.lkpower.pis.data.protocol.TaskConveyDetail
import kotlinx.android.synthetic.main.layout_publish_item.view.*

class TaskConveyAdapter(context: Context) : BaseRecyclerViewAdapter<TaskConveyDetail, TaskConveyAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_publish_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        holder.itemView.mDutyUserTv.text = model.PublisherName // 发布人姓名
        holder.itemView.mTitleTv.text = model.TaskTitle
        holder.itemView.mSubmitTimeTv.text = model.PublishDate // 任务发布时间
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}