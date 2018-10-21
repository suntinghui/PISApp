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
import kotlinx.android.synthetic.main.activity_zyrw_task.*
import org.jetbrains.anko.toast

class ZYRWTaskInfoActivity : BaseActivity() {

    private val TASK_STATUS_LIS = listOf<String>("已完成", "未完成")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zyrw_task)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()

        PictureFileUtils.deleteCacheDirFile(this);
    }

    private fun initView() {

        mStateTv.onClick { selectTaskStatus() }

        mSendBtn.isShadowEnabled = true
        mSendBtn.shadowHeight = 5
        mSendBtn.cornerRadius = 5
        mSendBtn.onClick { sendAction() }

    }

    private fun sendAction() {
        toast("=======")
    }

    private fun selectTaskStatus() {
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mStateTv.text = TASK_STATUS_LIS.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(TASK_STATUS_LIS)
        pickerView.setSelectOptions(TASK_STATUS_LIS.indexOf(mStateTv.text))
        pickerView.show()
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