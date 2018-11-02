package com.lkpower.pis.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lkpower.pis.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.Category
import kotlinx.android.synthetic.main.layout_category_item.view.*

class CategoryAdapter(context:Context) : BaseRecyclerViewAdapter<Category, CategoryAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_category_item, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]
        holder.itemView.mCategoryIconIv.setImageResource(model.icon)
        holder.itemView.mCategoryTitleTv.text = model.title
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}