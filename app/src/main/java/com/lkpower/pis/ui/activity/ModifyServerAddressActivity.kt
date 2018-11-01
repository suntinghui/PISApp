package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.text.Editable
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.onClick
import com.lkpower.pis.R
import com.lkpower.base.utils.PISUtil
import com.lkpower.base.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_modify_server_address.*
import org.jetbrains.anko.toast

class ModifyServerAddressActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_modify_server_address)

        initView()
    }

    private fun initView() {
        mAddressEt.text = Editable.Factory.getInstance().newEditable(PISUtil.getUserInputAddress())

        mSendBtn.isShadowEnabled = true
        mSendBtn.shadowHeight = 5
        mSendBtn.cornerRadius = 5
        mSendBtn.onClick { sendAction() }
    }

    private fun sendAction() {
        if (mAddressEt.text.toString().trim().isNullOrEmpty()) {
            ViewUtils.warning(this,"请输入服务器地址")
            return
        }

        PISUtil.setUserInputAddress(mAddressEt.text.toString().trim())

        ViewUtils.success(this, "服务器地址已修改")

        finish()
    }

}