package com.lkpower.pis.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.blankj.utilcode.util.AppUtils
import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.data.api.SettingApi
import com.lkpower.pis.data.protocol.FirAppInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UpdateUtil {

    // 检查更新
    fun checkLatestVersion(context: Activity, isLastestTip:Boolean = true) {
        if (!NetWorkUtils.isNetWorkAvailable(context)) {
            ViewUtils.warning(context, "当前网络不可用，请稍候再试")
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
                    AlertView("提示", "发现新版本，立即更新", null, arrayOf("确定"), null, context, AlertView.Style.Alert, OnItemClickListener { o, position ->
                        //getDownloadToken()

                        installAppWithFir(context)
                    }).show();

                } else {
                    if (isLastestTip) {
                        ViewUtils.success(context, "当前已经是最新版本")
                    }
                }
            }

            override fun onFailure(call: Call<FirAppInfo>, t: Throwable) {
                ViewUtils.showSimpleAlert(context, "检查更新时出现异常，请稍候再试")
            }
        })
    }

    // 方法一:通过获取到download_token得到下载地址,然后打开浏览器自动下载
    private fun getDownloadToken(context: Activity) {
        if (!NetWorkUtils.isNetWorkAvailable(context)) {
            ViewUtils.warning(context, "当前网络不可用，请稍候再试")
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
                installApp(context, response.body()!!.download_token)
            }

            override fun onFailure(call: Call<FirAppInfo>, t: Throwable) {
                ViewUtils.showSimpleAlert(context, "下载出错，请稍候再试")
            }
        })
    }

    private fun installApp(context: Activity, download_token: String) {
        var downloadUrl = "http://download.fir.im/apps/${BaseConstant.FIR_APP_ID}/install?download_token=$download_token"

        var intent = Intent().setAction("android.intent.action.VIEW")
        var content_url: Uri = Uri.parse(downloadUrl)
        intent.setData(content_url)
        context.startActivity(intent)
    }

    // 方法二:判断有新版本后,直接打开应用在fir上的地址,用户手机下载
    private fun installAppWithFir(context: Activity) {
        var intent = Intent().setAction("android.intent.action.VIEW")
        var content_url: Uri = Uri.parse("https://fir.im/9eha")
        intent.setData(content_url)
        context.startActivity(intent)
    }
}