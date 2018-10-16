package com.lkpower.pis.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.kotlin.base.ui.activity.BaseActivity
import com.lkpower.base.ext.onClick
import com.lkpower.pis.R
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.tools.PictureFileUtils
import kotlinx.android.synthetic.main.activity_fault_history_confirm.*
import kotlinx.android.synthetic.main.activity_fault_history_list.*
import org.jetbrains.anko.toast

@Route(path = "/pis/FaultReportActivity")
class FaultReportActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ARouter.getInstance().inject(this)

        setContentView(R.layout.activity_fault_report)

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()

        PictureFileUtils.deleteCacheDirFile(this);
    }

    private fun initView() {
        mTypeTv.onClick { showTypeEvent() }
        mModelTv.onClick { showModelEvent() }

        mSendBtn.setShadowEnabled(true);
        mSendBtn.setShadowHeight(5);
        mSendBtn.setCornerRadius(5);
        mSendBtn.onClick { sendAction() }
    }

    // 选择车型
    private fun showTypeEvent() {
        var list: List<String> = listOf("item1", "item2", "item3")
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mTypeTv.text = list.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(list)
        pickerView.setSelectOptions(list.indexOf(mTypeTv.text))
        pickerView.show()
    }

    // 选择车种
    private fun showModelEvent() {
        var list: List<String> = listOf("item1", "item2", "item3")
        var pickerView = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            mModelTv.text = list.get(options1)
        }
        ).build<String>()
        pickerView.setPicker(list)
        pickerView.setSelectOptions(list.indexOf(mModelTv.text))
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