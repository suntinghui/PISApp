package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lkpower.pis.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.ext.setVisible
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.DrivingInfo
import kotlinx.android.synthetic.main.layout_driving_info_item.view.*

class DrivingInfoAdapter(context: Context) : BaseRecyclerViewAdapter<DrivingInfo, DrivingInfoAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_driving_info_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        holder.itemView.mCarNumberNameTv.text = model.CarNumberName
        holder.itemView.mGroupNameTv.text = model.GroupName
        if (model.Remark.isNullOrEmpty()) {
            holder.itemView.mRemarkTv.setVisible(false)
        } else {
            holder.itemView.mRemarkTv.setVisible(true)
            holder.itemView.mRemarkTv.text = model.Remark
        }
        holder.itemView.mSubmitTimeTv.text = model.SubmitTime
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}