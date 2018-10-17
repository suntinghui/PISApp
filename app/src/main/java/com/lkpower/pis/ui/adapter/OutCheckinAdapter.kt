package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.OutCheckInInfo
import com.lkpower.pis.data.protocol.PublishInfo
import kotlinx.android.synthetic.main.layout_out_checkin_item.view.*
import kotlinx.android.synthetic.main.layout_publish_item.view.*

class OutCheckinAdapter(context: Context) : BaseRecyclerViewAdapter<OutCheckInInfo, OutCheckinAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_out_checkin_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        holder.itemView.mStationNameTv.text = model.SiteName
        holder.itemView.mStatusTv.text = model.TaskStatus
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}