package com.kotlin.base.widgets

import android.app.Activity
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.base.R
import com.lkpower.pis.ui.adapter.ImagePickerAdapter
import kotlinx.android.synthetic.main.layout_imagepicker_view.view.*
import com.lkpower.base.widgets.RecyclerItemDecoration
import com.lkpower.pis.ui.adapter.ShowImageAdapter
import com.luck.picture.lib.entity.LocalMedia


class ShowImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var mAdapter:ShowImageAdapter
    private lateinit var imageList:MutableList<String>

    //初始化属性
    init {
        imageList = mutableListOf<String>()

        initView()
    }

    //初始化视图
    private fun initView() {
        View.inflate(context, R.layout.layout_imagepicker_view, this)

        mImagePickerRv.layoutManager = GridLayoutManager(context, 3)
        mImagePickerRv.addItemDecoration(RecyclerItemDecoration(20, 10))

        mAdapter = ShowImageAdapter(context)
        mImagePickerRv.adapter = mAdapter

        mAdapter.setData(imageList)

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<String> {
            override fun onItemClick(path: String, position: Int) {
                if (path.isNullOrEmpty().not()) {
                    previewImage(path)
                }
            }
        })
    }

    fun setImageData (list:List<String>) {
        imageList = list.toMutableList()
        mAdapter.setData(imageList)
    }

    fun previewImage(path:String) {
        ARouter.getInstance()
                .build("/pis/PreviewImageActivity")
                .withString("path", path)
                .withBoolean("ShowDelete", false)
                .navigation()
    }

}
