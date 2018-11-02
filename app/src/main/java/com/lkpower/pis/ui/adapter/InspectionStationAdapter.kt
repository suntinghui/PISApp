package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lkpower.pis.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.XJ_CZSL
import com.lkpower.pis.utils.PISUtil
import kotlinx.android.synthetic.main.layout_station_item.view.*

class InspectionStationAdapter(context: Context) : BaseRecyclerViewAdapter<XJ_CZSL, InspectionStationAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_station_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        holder.itemView.mIndexTv.text = "${position + 1}"
        holder.itemView.mStationNameTv.text = model.stationName
        holder.itemView.mArrivalTimeTv.text = model.arrivalTime
        holder.itemView.mStateTv.text = PISUtil.getInspectionStationState(model.State)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}