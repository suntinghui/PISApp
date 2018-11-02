package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lkpower.pis.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.PublishInfo
import kotlinx.android.synthetic.main.layout_publish_item.view.*

class PublishAdapter(context: Context) : BaseRecyclerViewAdapter<PublishInfo, PublishAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_publish_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mTitleTv.text = model.Title
        // 1：已读；0：未读
        if (model.IsRead == "1") {
            holder.itemView.mTitleTv.setTextColor(mContext.resources.getColor(R.color.text_normal))
        } else {
            holder.itemView.mTitleTv.setTextColor(mContext.resources.getColor(R.color.text_dark))
        }

        holder.itemView.mDutyUserTv.text = model.DutyUser
        holder.itemView.mSubmitTimeTv.text = model.SubmitTime
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}