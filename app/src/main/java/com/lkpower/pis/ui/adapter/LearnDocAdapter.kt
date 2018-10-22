package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.LearnDoc
import com.lkpower.pis.data.protocol.PublishInfo
import kotlinx.android.synthetic.main.layout_publish_item.view.*

class LearnDocAdapter(context: Context) : BaseRecyclerViewAdapter<LearnDoc, LearnDocAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_learn_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        holder.itemView.mDutyUserTv.text = model.UploaderName
        holder.itemView.mTitleTv.text = model.DocTitle
        if (model.ReadState == "0") { // 未读=0，已读=1
            holder.itemView.mTitleTv.setTextColor(mContext.resources.getColor(R.color.text_dark))
        } else {
            holder.itemView.mTitleTv.setTextColor(mContext.resources.getColor(R.color.text_light_dark))
        }
        holder.itemView.mSubmitTimeTv.text = model.UploadDate
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}