package com.lkpower.pis.ui.activity

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.onClick
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.injection.component.DaggerUserComponent
import com.lkpower.pis.injection.module.UserModule
import com.lkpower.pis.presenter.LoginPresenter
import com.lkpower.pis.presenter.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        mUsernameEt.setText(AppPrefsUtils.getString(BaseConstant.kUsername))
        mPasswordEt.setText(AppPrefsUtils.getString(BaseConstant.kPassword))

        mLoginBtn.setButtonColor(getResources().getColor(R.color.fbutton_color_carrot))
        mLoginBtn.setShadowEnabled(true)
        mLoginBtn.setShadowHeight(5)
        mLoginBtn.setCornerRadius(5)
        mLoginBtn.onClick(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mLoginBtn -> {
                if (checkInput()) {
                    mPresenter.login(mUsernameEt.text.toString(), mPasswordEt.text.toString(), "")
                }
            }
        }
    }

    private fun checkInput(): Boolean {
        if (mUsernameEt.text.isNullOrEmpty()) {
            toast("请输入用户名")
            return false
        } else if (mPasswordEt.text.isNullOrEmpty()) {
            toast("请输入密码")
            return false
        }
        return true
    }

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onLoginResult(result: UserInfo) {
        AppPrefsUtils.putString(BaseConstant.kUsername, mUsernameEt.text.toString())
        AppPrefsUtils.putString(BaseConstant.kPassword, mPasswordEt.text.toString())

        AppPrefsUtils.putString(BaseConstant.kEmpId, result.EmpId)
        AppPrefsUtils.putString(BaseConstant.kTokenKey, result.TokenKey)

        startActivity<PrimaryCategoryActivity>()
    }

}
