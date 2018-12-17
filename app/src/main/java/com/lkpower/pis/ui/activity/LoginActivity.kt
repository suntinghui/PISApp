package com.lkpower.pis.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AppUtils
import com.lkpower.pis.ui.activity.BaseMvpActivity
import com.lkpower.pis.utils.AppPrefsUtils
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.utils.PISUtil
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.injection.component.DaggerUserComponent
import com.lkpower.pis.injection.module.UserModule
import com.lkpower.pis.presenter.LoginPresenter
import com.lkpower.pis.presenter.view.LoginView
import com.lkpower.pis.utils.UpdateUtil
import com.orhanobut.logger.Logger
import com.umeng.analytics.MobclickAgent
import com.umeng.message.IUmengCallback
import com.umeng.message.PushAgent
import com.umeng.message.UTrack
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

@Route(path = "/pis/LoginActivity")
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        mSettingIv.onClick { settingIpAction() }

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
                    mPresenter.login(mUsernameEt.text.toString(), mPasswordEt.text.toString(), PISUtil.getDeviceId(this), AppUtils.getAppVersionName())
                }
            }
        }
    }

    private fun settingIpAction() {
        startActivity<ModifyServerAddressActivity>()
    }

    private fun checkInput(): Boolean {
        if (mUsernameEt.text.isNullOrEmpty()) {
            ViewUtils.warning(this, "请输入用户名")
            return false
        } else if (mPasswordEt.text.isNullOrEmpty()) {
            ViewUtils.warning(this, "请输入密码")
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
        PISUtil.setTokenKey(result.TokenKey)

        MobclickAgent.onProfileSignIn(mUsernameEt.text.toString())

        registerUMengAlias()

        startActivity<PrimaryCategoryActivity>()
    }

    /*
        每个类型的alias同时只能存在一个，新的会覆盖掉旧的，所以不需要删除旧的
     */
    private fun registerUMengAlias() {
        var mPushAgent: PushAgent = PushAgent.getInstance(this)

        // 退出登录后会关闭推送，所以此时应该打开推送
        mPushAgent.enable(object : IUmengCallback{
            override fun onSuccess() {
                Logger.e("打开推送：成功")
            }

            override fun onFailure(p0: String?, p1: String?) {
                Logger.e("打开推送：失败")
            }
        })

        mPushAgent.addAlias(AppPrefsUtils.getString(BaseConstant.kEmpId), "USER_ID", object : UTrack.ICallBack {
            override fun onMessage(isSuccess: Boolean, message: String?) {
                Logger.e("Alias USER_ID $message - $isSuccess")
            }
        })

        mPushAgent.addAlias(PISUtil.getDeviceId(this), "DeviceID", object : UTrack.ICallBack {
            override fun onMessage(isSuccess: Boolean, message: String?) {
                Logger.e("Alias DeviceID $message - $isSuccess")
            }
        })


    }

}
