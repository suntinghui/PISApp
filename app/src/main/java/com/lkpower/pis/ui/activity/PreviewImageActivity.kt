package com.lkpower.pis.ui.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.eightbitlab.rxbus.Bus
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.event.DeleteSelectImageEvent
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.ext.setVisible
import com.lkpower.pis.utils.FileUtils
import com.lkpower.pis.utils.ImageViewUtil
import com.lkpower.pis.R
import com.lkpower.pis.injection.component.DaggerAttachmentComponent
import com.lkpower.pis.injection.module.AttachmentModule
import com.lkpower.pis.presenter.AttachmentDeletePresenter
import com.lkpower.pis.presenter.view.AttachmentDeleteView
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_preview.*
import me.panpf.sketch.decode.ImageAttrs
import me.panpf.sketch.request.CancelCause
import me.panpf.sketch.request.DisplayListener
import me.panpf.sketch.request.ErrorCause
import me.panpf.sketch.request.ImageFrom
import org.jetbrains.anko.toast
import java.lang.Exception

@Route(path = "/pis/PreviewImageActivity")
class PreviewImageActivity : BaseMvpActivity<AttachmentDeletePresenter>(), AttachmentDeleteView {

    @Autowired
    @JvmField
    var path: String = ""

    @Autowired
    @JvmField
    var ShowDelete: Boolean = false

    @Autowired
    @JvmField
    var attId: String = ""

    @Autowired
    @JvmField
    var attType: String = BaseConstant.Att_Type_Other

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_preview)

        initView()
    }

    private fun initView() {
        mLeftIv.onClick { this.finish() }

        mDeleteBtn.setVisible(ShowDelete)

        mDeleteBtn.setButtonColor(getResources().getColor(R.color.fbutton_color_alizarin));
        mDeleteBtn.setShadowEnabled(false);
        mDeleteBtn.onClick { deleteAction() }


        mImageView.isZoomEnabled = true
        mImageView.getZoomer()?.zoom(3f, true)

        // setDisplayListener() 一定要在 displayImage() 之前
        mImageView.displayListener = object : DisplayListener {
            override fun onStarted() {
                this@PreviewImageActivity.showLoading()
            }

            override fun onCanceled(cause: CancelCause) {
                this@PreviewImageActivity.hideLoading()
                ViewUtils.success(this@PreviewImageActivity, "加载图片过程被取消")
            }

            override fun onError(cause: ErrorCause) {
                this@PreviewImageActivity.hideLoading()
                ViewUtils.error(this@PreviewImageActivity, "图片加载失败")
            }

            override fun onCompleted(drawable: Drawable, imageFrom: ImageFrom, imageAttrs: ImageAttrs) {
                this@PreviewImageActivity.hideLoading()
            }

        }
        mImageView.displayImage(path)
    }

    private fun deleteAction() {
        var msg = if (FileUtils.isNetFile(path)) "将删除服务器上已上传的图片" else "仅删除选中状态，不会删除本地图片"

        AlertView("确定删除？", msg, "取消", arrayOf("确定"), null, this@PreviewImageActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
            when (position) {
                0 -> {
                    if (FileUtils.isNetFile(path)) {
                        if (attId.isEmpty() || attType.isEmpty()) {
                            ViewUtils.warning(this@PreviewImageActivity, "无法删除图片，请返回重试")
                        } else {
                            mPresenter.deleteFile(attId, attType, PISUtil.getTokenKey())
                        }

                    } else {
                        Bus.send(DeleteSelectImageEvent(path))
                        finish()
                    }
                }
            }
        }).show();
    }

    override fun injectComponent() {
        DaggerAttachmentComponent.builder().activityComponent(mActivityComponent).attachmentModule(AttachmentModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onDeleteResult(result: Boolean) {
        ViewUtils.success(this, "删除成功")

        Bus.send(DeleteSelectImageEvent(path))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            ImageViewUtil.releaseImageResource(mImageView)

            if (mRootLayout != null && mImageView != null) {
                mRootLayout.removeView(mImageView)
                //mImageView = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}