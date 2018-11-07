package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.text.Editable
import com.lkpower.pis.ui.activity.BaseActivity
import com.lkpower.pis.utils.AppPrefsUtils
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.R
import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils
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

        mSendBtn.setButtonColor(getResources().getColor(R.color.fbutton_color_alizarin))
        mSendBtn.isShadowEnabled = true
        mSendBtn.shadowHeight = 5
        mSendBtn.cornerRadius = 5
        mSendBtn.onClick { sendAction() }
    }

    private fun sendAction() {
        if (mAddressEt.text.toString().trim().isNullOrEmpty()) {
            ViewUtils.warning(this, "请输入服务器地址")
            return
        }

        PISUtil.setUserInputAddress(mAddressEt.text.toString().trim())

        ViewUtils.success(this, "服务器地址已修改")

        RetrofitFactory.instance.resetRetrofit()

        finish()
    }

}