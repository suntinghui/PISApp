package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.DrivingInfo
import com.lkpower.pis.data.protocol.EmergencyInfo
import kotlinx.android.synthetic.main.layout_driving_info_item.view.*

class EmergencyInfoAdapter(context: Context) : BaseRecyclerViewAdapter<EmergencyInfo, EmergencyInfoAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_emergency_info_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mCarNumberNameTv.text = model.CarNumberName + "#" + model.GroupName
        holder.itemView.mGroupNameTv.text = model.FeedBackUserName
        holder.itemView.mSubmitTimeTv.text = model.FeedBackTime
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}