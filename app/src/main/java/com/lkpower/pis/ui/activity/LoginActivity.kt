package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.onClick
import com.lkpower.pis.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        mUsernameEt.setText(AppPrefsUtils.getString(BaseConstant.kUSERNAME))
        mPasswordEt.setText(AppPrefsUtils.getString(BaseConstant.kPASSWORD))

        mLoginBtn.setButtonColor(getResources().getColor(R.color.fbutton_color_carrot));
        mLoginBtn.setShadowEnabled(true);
        mLoginBtn.setShadowHeight(5);
        mLoginBtn.setCornerRadius(5);
        mLoginBtn.onClick(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mLoginBtn -> {
                if (checkInput()) {
                    AppPrefsUtils.putString(BaseConstant.kUSERNAME, mUsernameEt.text.toString())
                    AppPrefsUtils.putString(BaseConstant.kPASSWORD, mPasswordEt.text.toString())
                    startActivity<PrimaryCategoryActivity>()
                }
            }
        }

    }

    private fun checkInput():Boolean {
        if (mUsernameEt.text.isNullOrEmpty()) {
            toast("请输入用户名")
            return false
        } else if (mPasswordEt.text.isNullOrEmpty()) {
            toast("请输入密码")
            return false
        }
        return true
    }
}
