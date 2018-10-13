package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.PublishInfo
import kotlinx.android.synthetic.main.layout_publish_item.view.*

class PublishAdapter(context:Context) : BaseRecyclerViewAdapter<PublishInfo, PublishAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_publish_item, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        holder.itemView.mDutyUserTv.text = model.DutyUser
        holder.itemView.mTitleTv.text = model.Title
        holder.itemView.mSubmitTimeTv.text = model.SubmitTime
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}