package com.lkpower.pis.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lkpower.pis.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.common.BaseApplication.Companion.context
import com.lkpower.pis.common.GlideApp
import com.luck.picture.lib.entity.LocalMedia
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.layout_imagepicker_item.view.*

class ShowImageAdapter(context:Context) : BaseRecyclerViewAdapter<String, ShowImageAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_show_image_item, parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val path = dataList[position]
        if (path.isNullOrEmpty().not()) {
            GlideApp.with(context)
                    .load(path)
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.icon_placeholder)
                    .fitCenter()
                    .into(holder.itemView.mImageView);
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}