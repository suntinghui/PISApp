package com.lkpower.pis.widgets

import android.app.Activity
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.lkpower.pis.ui.adapter.BaseRecyclerViewAdapter
import com.lkpower.pis.R
import com.lkpower.pis.ui.adapter.ImagePickerAdapter
import kotlinx.android.synthetic.main.layout_imagepicker_view.view.*
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.data.api.AttApi
import com.lkpower.pis.data.protocol.AttModel
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.event.DeleteSelectImageEvent
import com.lkpower.pis.utils.FileUtils
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.tools.PictureFileUtils
import com.orhanobut.logger.Logger
import com.lkpower.pis.widgets.RecyclerItemDecoration
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

/*

1、此控件的作用是显示图片并在相册或是通过拍照选择照片
2、显示的图片有可能是来自于服务器传过来的
3、在预览图片时可以删除图片，如果是网上传过来的照片则调用删除接口删除该图片，如果是本地的图片直接取消选择即可
4、最多可以选择的张数需要减去网络上的图片


 */

class ImagePickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    lateinit var mAdapter: ImagePickerAdapter
    lateinit var localList: MutableList<LocalMedia> // 本地图片
    lateinit var netList: MutableList<LocalMedia> // 网络图片列表

    lateinit var netAttList: List<AttModel> // 网络图片列表AttModel

    private var isEditable: Boolean = true
    private var attType = "1"

    private lateinit var onUploadListener: OnUploadListener
    private lateinit var onImageClickLisenter: OnImageClickLisenter

    //初始化属性
    init {
        localList = mutableListOf()
        netList = mutableListOf()
        netAttList = listOf()

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
                Logger.e("===========================================================+")
                if (this@ImagePickerView::onImageClickLisenter.isInitialized) {
                    this@ImagePickerView.onImageClickLisenter.onClick(item.path)
                } else {
                    Logger.e("onImageClickLisenter未被初始化。。。"+position)
                }

                if (item.path.isNullOrEmpty()) {
                    showPickerView()
                } else {
                    preAndDeleteImage(item)
                }
            }
        })
    }

    private fun refreshData() {
        if (localList.isNotEmpty())
            localList.removeAll { it.path.isNullOrEmpty() }

        if (isEditable && localList.size < BaseConstant.PickerImageSize - netList.size) {
            localList.add(getEmptyLocalMedia())
        }

        mAdapter.setData(generateImageList())
    }

    // 当本地或是网络图片发生变化时，重新生成整个imageList
    fun generateImageList(): MutableList<LocalMedia> {
        return netList.plus(localList).toMutableList()
    }

    // 设置网络图片列表
    public fun setNetImages(attList: List<AttModel>) {
        netAttList = attList
        netList.clear()

        attList.map {
            var localMedia = LocalMedia()
            localMedia.path = it.DownLoadUrl
            netList.add(localMedia)
        }

        refreshData()
    }

    // 设置该控件是否可编辑
    public fun setEditable(editable: Boolean): ImagePickerView {
        this.isEditable = editable
        return this
    }

    public fun setAttType(attType: String): ImagePickerView {
        this.attType = attType
        return this
    }

    public fun setOnUploadListener(listener: OnUploadListener) {
        this.onUploadListener = listener
    }

    public fun setOnImageClickListener(listener: OnImageClickLisenter) {
        this.onImageClickLisenter = listener
    }

    // 外部调用该接口后开始上传图片
    public fun uploadAction(busId: String, attType: String, tokenKey: String) {
        // 如果没有可以上传的图片,则直接调用onComplete()
        if (localList.isEmpty()) {
            this.onUploadListener.onComplete()
            return
        }

        val mApi = RetrofitFactory.instance.create(AttApi::class.java)

        var observableList = mutableListOf<Observable<BaseResp<List<AttModel>>>>()
        localList.filter { it.path.isNotEmpty() }.map {
            val busIdRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), busId)
            val attTypeRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), attType)
            val tokenKeyRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), tokenKey)

            val file: File = File(if (it.isCompressed) it.compressPath else it.path)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val requestFileBody: MultipartBody.Part = MultipartBody.Part.createFormData(file.getName(), file.getName(), requestFile);

            var observable: Observable<BaseResp<List<AttModel>>> = mApi.uploadFiles(busIdRequestBody, attTypeRequestBody, tokenKeyRequestBody, requestFileBody)
            observableList.add(observable)
        }

        Observable.mergeDelayError(observableList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseResp<List<AttModel>>> {
                    override fun onComplete() {
                        Logger.e("===========onComplete")
                        // 上传成功后要删除所有的本地选择的图片
                        localList.clear()
                        this@ImagePickerView.onUploadListener.onComplete()
                    }

                    override fun onSubscribe(d: Disposable) {
                        Logger.e("===========onSubscribe")
                    }

                    override fun onNext(t: BaseResp<List<AttModel>>) {
                        Logger.e("===========onNext  " + t.toString())
                    }

                    override fun onError(e: Throwable) {
                        Logger.e("===========onError")
                        e.printStackTrace()
                        this@ImagePickerView.onUploadListener.onError()
                    }

                })
    }

    private fun showPickerView() {
        PictureFileUtils.deleteCacheDirFile(context)

        PictureSelector.create(context as Activity)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(BaseConstant.PickerImageSize - netList.size) // 最大可选择的数量需要减去网络上的图片
                .minSelectNum(1)
                .imageSpanCount(3)
                .selectionMode(PictureConfig.MULTIPLE)
                .selectionMedia(localList.filter { it.path.isNotEmpty() })
                .previewImage(false)
                .isCamera(true)
                .compress(true)
                .synOrAsy(false)
                .cropCompressQuality(10)
                .minimumCompressSize(100)
                .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    fun onPickerDoneResult(list: List<LocalMedia>) {
        localList = list.toMutableList()
        refreshData()
    }

    // 预览图片，并设置是否可删除
    fun preAndDeleteImage(item: LocalMedia) {
        var attId = ""
        var attType = ""

        if (FileUtils.isNetFile(item.path)) {
            var attModel = netAttList.filter { it.DownLoadUrl == item.path }.first()
            attId = attModel.AttId
            attType = this.attType
        }

        ARouter.getInstance()
                .build("/pis/PreviewImageActivity")
                .withString("path", item.path)
                .withBoolean("ShowDelete", isEditable)
                .withString("attId", attId)
                .withString("attType", attType)
                .navigation()
    }

    // 注册删除事件
    fun registerDeleteEvent() {
        Bus.observe<DeleteSelectImageEvent>()
                .subscribe { t: DeleteSelectImageEvent ->
                    run {
                        // 为了节省一个Event，采用判断要删除的文件在本地和网络列表中都试探一下的方式
                        if (netList.any { it.path == t.deletePath })
                            netList.removeAt(netList.map { it.path }.indexOf(t.deletePath))

                        if (localList.any { it.path == t.deletePath })
                            localList.removeAt(localList.map { it.path }.indexOf(t.deletePath))

                        refreshData()
                    }
                }
                .registerInBus(context)
    }

    fun getEmptyLocalMedia(): LocalMedia {
        var empty = LocalMedia()
        empty.path = ""
        empty.compressPath = ""
        empty.cutPath = ""
        return empty
    }

    interface OnUploadListener {
        fun onError()
        fun onComplete()
    }

    interface OnImageClickLisenter {
        fun onClick(path: String?)
    }

}
