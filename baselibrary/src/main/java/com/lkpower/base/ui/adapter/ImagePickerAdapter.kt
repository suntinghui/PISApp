package com.lkpower.pis.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.R
import com.luck.picture.lib.entity.LocalMedia
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.layout_imagepicker_item.view.*

class ImagePickerAdapter(context:Context) : BaseRecyclerViewAdapter<LocalMedia, ImagePickerAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_imagepicker_item, parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = dataList[position]
        Logger.e(""+position+"==="+dataList.get(position).path)
        if (item.path.isNullOrEmpty().not()) {
            holder.itemView.mImageView.setImageBitmap(BitmapFactory.decodeFile(if (item.isCompressed) item.compressPath else item.path))
            holder.itemView.mLayout.setBackgroundResource(R.drawable.border_all)
        } else {
            holder.itemView.mImageView.setImageBitmap(null)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}