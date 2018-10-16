package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.kotlin.base.ui.activity.BaseActivity
import com.lkpower.base.ext.onClick
import com.lkpower.pis.R
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.tools.PictureFileUtils
import kotlinx.android.synthetic.main.activity_fault_history_confirm.*
import org.jetbrains.anko.toast

class FaultHistoryConfirmActivity : BaseActivity() {

    private val CONFIRM_STATUS_LIS = listOf<String>("未修复", "已修复")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fault_history_confirm)

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()

        PictureFileUtils.deleteCacheDirFile(this);
    }

    private fun initView() {
        mTypeView.setContentText("T40")
        mModelView.setContentText("usdt暴跌")
        mNumView.setContentText("科大讯飞停运整改")
        mRemarkView.setContentText("男子暴击医生被抓男子暴击医生被抓男子暴击医生被抓男子暴击医生被抓")
        mDescView.setContentText("海天回应酱油质量")

        mFaultPicView.getLayout().background = null // 去除文字故障图片下的横线
        mShowImageView.setImageData(listOf<String>("http://img.zcool.cn/community/0117e2571b8b246ac72538120dd8a4.jpg@1280w_1l_2o_100sh.jpg",
                "http://img.zcool.cn/community/0117e2571b8b246ac72538120dd8a4.jpg@1280w_1l_2o_100sh.jpg",
                "http://img.zcool.cn/community/0117e2571b8b246ac72538120dd8a4.jpg@1280w_1l_2o_100sh.jpg",
                "http://img.zcool.cn/community/0117e2571b8b246ac72538120dd8a4.jpg@1280w_1l_2o_100sh.jpg",
                "http://img.zcool.cn/community/0117e2571b8b246ac72538120dd8a4.jpg@1280w_1l_2o_100sh.jpg"))


        mConfirmTv.text = CONFIRM_STATUS_LIS.first()
        mConfirmTv.onClick { selectConfirmStatus() }

        mSendBtn.setShadowEnabled(true);
        mSendBtn.setShadowHeight(5);
        mSendBtn.setCornerRadius(5);
        mSendBtn.onClick { sendAction() }

    }

    private fun selectConfirmStatus() {
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mConfirmTv.text = CONFIRM_STATUS_LIS.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(CONFIRM_STATUS_LIS)
        pickerView.setSelectOptions(CONFIRM_STATUS_LIS.indexOf(mConfirmTv.text))
        pickerView.show()
    }

    private fun sendAction() {
        toast("=======")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    mImagePicker.onPickerDoneResult(selectList)
                }
            }
        }
    }
}