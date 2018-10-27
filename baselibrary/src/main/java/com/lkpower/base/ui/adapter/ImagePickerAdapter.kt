package com.lkpower.pis.ui.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.R
import com.lkpower.base.common.BaseApplication
import com.lkpower.base.common.BaseApplication.Companion.context
import com.lkpower.base.common.GlideApp
import com.luck.picture.lib.entity.LocalMedia
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.layout_imagepicker_item.view.*

/*
如果是http开头则认为是网络图片，否则是本地图片

 */
class ImagePickerAdapter(context: Context) : BaseRecyclerViewAdapter<LocalMedia, ImagePickerAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_imagepicker_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = dataList[position]
        Logger.e("" + position + "===" + dataList.get(position).path)
        if (item.path.isNullOrEmpty().not()) {
            GlideApp.with(context)
                    .load(if (item.isCompressed) item.compressPath else item.path)
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.icon_placeholder)
                    .fitCenter()
                    .into(holder.itemView.mImageView)

            holder.itemView.mLayout.setBackgroundResource(R.drawable.border_all)
        } else {
            holder.itemView.mImageView.setImageBitmap(null)
            holder.itemView.mLayout.setBackgroundResource(R.drawable.icon_upload)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}