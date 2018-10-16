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
import com.bumptech.glide.Glide
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.lkpower.base.event.DeleteSelectImageEvent
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.tools.PictureFileUtils
import com.orhanobut.logger.Logger
import com.lkpower.base.widgets.RecyclerItemDecoration




class ImagePickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    lateinit var mAdapter:ImagePickerAdapter
    lateinit var imageList:MutableList<LocalMedia>

    //初始化属性
    init {
        imageList = mutableListOf()

        initView()

        registerDeleteEvent()
    }

    //初始化视图
    private fun initView() {
        View.inflate(context, R.layout.layout_imagepicker_view, this)

        mImagePickerRv.layoutManager = GridLayoutManager(context, 3)
        mImagePickerRv.addItemDecoration(RecyclerItemDecoration(20, 10))

        mAdapter = ImagePickerAdapter(context)
        mImagePickerRv.adapter = mAdapter

        mAdapter.setData(mutableListOf<LocalMedia>(getEmptyLocalMedia()))

        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<LocalMedia> {
            override fun onItemClick(item: LocalMedia, position: Int) {
                if (item.path.isNullOrEmpty()) {
                    showPickerView()
                } else {
                    preAndDeleteImage(item)
                }
            }
        })
    }

    private fun refreshData() {
        imageList.removeAll { it.path.isNullOrEmpty() }

        if (imageList.size<9) {
            imageList.add(getEmptyLocalMedia())
        }

        mAdapter.setData(imageList)
    }

    private fun showPickerView() {
        PictureFileUtils.deleteCacheDirFile(context)

        PictureSelector.create(context as Activity)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)
                .minSelectNum(1)
                .imageSpanCount(3)
                .selectionMode(PictureConfig.MULTIPLE)
                .selectionMedia(imageList.filter { it.path.isNotEmpty() })
                .previewImage(false)
                .isCamera(true)
                .compress(true)
                .synOrAsy(false)
                .cropCompressQuality(20)
                .minimumCompressSize(100)
                .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    fun onPickerDoneResult(list:List<LocalMedia>) {
        imageList = list.toMutableList()
        refreshData()
    }

    fun preAndDeleteImage(item:LocalMedia) {
        ARouter.getInstance()
                .build("/pis/PreviewImageActivity")
                .withString("path", item.path)
                .withBoolean("ShowDelete", true)
                .navigation()
    }

    fun registerDeleteEvent() {
        Bus.observe<DeleteSelectImageEvent>()
                .subscribe{
                    t: DeleteSelectImageEvent ->
                    run {
                        imageList.removeAt(imageList.map { it.path }.indexOf(t.deletePath))
                        refreshData()
                    }
                }
                .registerInBus(context)
    }

    fun getEmptyLocalMedia():LocalMedia{
        var empty = LocalMedia()
        empty.path = ""
        empty.compressPath = ""
        empty.cutPath = ""
        return empty
    }

}
