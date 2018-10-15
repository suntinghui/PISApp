package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.os.LocaleList
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.eightbitlab.rxbus.Bus
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.widgets.HeaderBar
import com.lkpower.base.event.DeleteSelectImageEvent
import com.lkpower.base.ext.onClick
import com.lkpower.base.utils.ImageViewUtil
import com.lkpower.pis.R
import com.luck.picture.lib.entity.LocalMedia
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_preview.*
import org.jetbrains.anko.toast

@Route(path = "/pis/PreviewImageActivity")
class PreviewImageActivity :BaseActivity() {

    @Autowired
    @JvmField
    var deletePath:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_preview)

        //ARouter注册
        ARouter.getInstance().inject(this)

        initView()
    }

    private fun initView() {
        mLeftIv.onClick { this.finish() }

        mDeleteBtn.setButtonColor(getResources().getColor(R.color.fbutton_color_alizarin));
        mDeleteBtn.setShadowEnabled(false);
        mDeleteBtn.onClick {
            AlertView("确定要删除吗？", "仅删除选中状态，不会删除本地图片", "取消", arrayOf("确定"), null, this@PreviewImageActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
                when (position) {
                    0->{
                        Bus.send(DeleteSelectImageEvent(deletePath))
                        finish()
                    }
                }
            }).show();

        }


        mImageView.isZoomEnabled = true
        mImageView.getZoomer()?.zoom(3f, true)
        mImageView.displayImage(deletePath)
    }

    override fun onDestroy() {
        super.onDestroy()

        ImageViewUtil.releaseImageResource(mImageView)

        if (mRootLayout != null && mImageView != null) {
            mRootLayout.removeView(mImageView)
            // mImageView = null
        }
    }
}