package com.lkpower.pis.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import com.bumptech.glide.Glide
import com.lkpower.pis.ui.activity.BaseActivity
import com.lkpower.pis.utils.NetWorkUtils
import com.liulishuo.filedownloader.FileDownloader
import com.lkpower.pis.common.AppManager
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.ext.onClick
import com.lkpower.pis.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.api.SettingApi
import com.lkpower.pis.data.protocol.FirAppInfo
import com.lkpower.pis.utils.UpdateUtil
import com.orhanobut.logger.Logger
import com.umeng.analytics.MobclickAgent
import com.umeng.message.IUmengCallback
import com.umeng.message.PushAgent
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Route(path = "/pis/SettingActivity")
class SettingActivity : BaseActivity(), View.OnClickListener {

    val PHONE_NUM = "0771-2769564"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ARouter.getInstance().inject(this)

        this.setContentView(R.layout.activity_setting)

        initView()
    }

    private fun initView() {
        mCurrentVersionTv.text = "当前版本：${AppUtils.getAppVersionName()}"
        mPhoneNumTv.text = PHONE_NUM

        mExitBtn.setButtonColor(getResources().getColor(R.color.fbutton_color_alizarin))
        mExitBtn.isShadowEnabled = true
        mExitBtn.shadowHeight = 5
        mExitBtn.cornerRadius = 5

        mCheckUpdateLayout.onClick(this)
        mClearCacheLayout.onClick(this)
        mFeedbackLayout.onClick(this)
        mCallMeLayout.onClick(this)
        mExitBtn.onClick(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mCheckUpdateLayout -> {
                UpdateUtil.checkLatestVersion(this)
            }
            R.id.mClearCacheLayout -> {
                clearAllCache()
            }
            R.id.mFeedbackLayout -> {
                startActivity<FeedbackActivity>()
            }
            R.id.mCallMeLayout -> {
                var intent = Intent(Intent.ACTION_DIAL)
                var data = Uri.parse("tel:$PHONE_NUM")
                intent.setData(data)
                startActivity(intent)
            }
            R.id.mExitBtn -> {
                AlertView("确定退出？", "退出登录后将不能再接收推送消息", "取消", arrayOf("确定"), null, this@SettingActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
                    when (position) {
                        0 -> {
                            logout()
                        }
                    }
                }).show();
            }
        }
    }

    private fun logout() {
        // 退出登录后不应该再接收推送
        var mPushAgent: PushAgent = PushAgent.getInstance(this)
        mPushAgent.disable(object:IUmengCallback{
            override fun onSuccess() {
                Logger.e("关闭推送：成功")
            }

            override fun onFailure(p0: String?, p1: String?) {
                Logger.e("关闭推送：失败")
            }
        })

        MobclickAgent.onProfileSignOff()

        AppManager.instance.finishAllActivity()

        startActivity<LoginActivity>()
    }

    private fun clearAllCache() {
        FileDownloader.setup(this)
        FileDownloader.getImpl().clearAllTaskData()

        FileUtils.deleteAllInDir(PathUtils.getExternalAppDownloadPath())

        runOnUiThread { Glide.get(this).clearMemory() }
        doAsync { Glide.get(this@SettingActivity).clearDiskCache() }

        ViewUtils.success(this, "已清空缓存")
    }

}


        