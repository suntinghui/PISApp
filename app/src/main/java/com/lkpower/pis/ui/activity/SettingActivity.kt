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
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.base.utils.NetWorkUtils
import com.liulishuo.filedownloader.FileDownloader
import com.lkpower.base.common.AppManager
import com.lkpower.base.common.BaseConstant
import com.lkpower.base.ext.onClick
import com.lkpower.base.utils.ViewUtils
import com.lkpower.pis.R
import com.lkpower.pis.data.api.SettingApi
import com.lkpower.pis.data.protocol.FirAppInfo
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

    val PHONE_NUM = "18501281696"

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
                checkLatestVersion()
            }
            R.id.mClearCacheLayout -> {
                clearAllCache()
            }
            R.id.mFeedbackLayout -> {
                startActivity<FeedbackActivity>()
            }
            R.id.mCallMeLayout -> {
                var intent = Intent(Intent.ACTION_DIAL);
                var data = Uri.parse("tel:$PHONE_NUM");
                intent.setData(data);
                startActivity(intent);
            }
            R.id.mExitBtn -> {
                AlertView("提示", "您确定要退出登录吗？", "取消", arrayOf("确定"), null, this@SettingActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
                    when (position) {
                        0 -> {
                            AppManager.instance.finishAllActivity()
                            startActivity<LoginActivity>()
                        }
                    }
                }).show();
            }
        }
    }

    private fun clearAllCache() {
        FileDownloader.setup(this)
        FileDownloader.getImpl().clearAllTaskData()

        FileUtils.deleteAllInDir(PathUtils.getExternalAppDownloadPath())

        runOnUiThread { Glide.get(this).clearMemory() }
        doAsync { Glide.get(this@SettingActivity).clearDiskCache() }

        toast("已清空缓存")
    }

    // 检查更新
    private fun checkLatestVersion() {
        if (!NetWorkUtils.isNetWorkAvailable(this)) {
            toast("当前网络不可用，请稍候再试")
            return
        }

        var retrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.FIR_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val request = retrofit.create(SettingApi::class.java)
        val call = request.queryLatest(BaseConstant.FIR_APP_TOKEN)
        call.enqueue(object : Callback<FirAppInfo> {
            override fun onResponse(call: Call<FirAppInfo>, response: Response<FirAppInfo>) {
                if (AppUtils.getAppVersionCode() < response.body()?.version!!.toInt()) {
                    // 开始更新
                    AlertView("提示", "发现新版本，立即更新", null, arrayOf("确定"), null, this@SettingActivity, AlertView.Style.Alert, OnItemClickListener { o, position ->
                        //getDownloadToken()

                        installAppWithFir()
                    }).show();

                } else {
                    toast("当前已经是最新版本")
                }
            }

            override fun onFailure(call: Call<FirAppInfo>, t: Throwable) {
                ViewUtils.showSimpleAlert(this@SettingActivity, "检查更新时出现异常，请稍候再试")
            }
        })
    }

    // 方法一:通过获取到download_token得到下载地址,然后打开浏览器自动下载
    private fun getDownloadToken() {
        if (!NetWorkUtils.isNetWorkAvailable(this)) {
            toast("当前网络不可用，请稍候再试")
            return
        }

        var retrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.FIR_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val request = retrofit.create(SettingApi::class.java)
        val call = request.getDownloadToken(BaseConstant.FIR_APP_TOKEN)
        call.enqueue(object : Callback<FirAppInfo> {
            override fun onResponse(call: Call<FirAppInfo>, response: Response<FirAppInfo>) {
                installApp(response.body()!!.download_token)
            }

            override fun onFailure(call: Call<FirAppInfo>, t: Throwable) {
                ViewUtils.showSimpleAlert(this@SettingActivity, "下载出错，请稍候再试")
            }
        })
    }

    private fun installApp(download_token: String) {
        var downloadUrl = "http://download.fir.im/apps/${BaseConstant.FIR_APP_ID}/install?download_token=$download_token"

        var intent = Intent().setAction("android.intent.action.VIEW")
        var content_url: Uri = Uri.parse(downloadUrl)
        intent.setData(content_url)
        startActivity(intent)
    }

    // 方法二:判断有新版本后,直接打开应用在fir上的地址,用户手机下载
    private fun installAppWithFir() {
        var intent = Intent().setAction("android.intent.action.VIEW")
        var content_url: Uri = Uri.parse("https://fir.im/9eha")
        intent.setData(content_url)
        startActivity(intent)
    }


}


        