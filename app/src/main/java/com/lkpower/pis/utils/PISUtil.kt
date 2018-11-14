package com.lkpower.pis.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.PermissionChecker
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import com.lkpower.pis.utils.AppPrefsUtils
import com.lkpower.pis.common.BaseConstant
import java.lang.Exception
import java.util.*

object PISUtil {

    fun setTokenKey(tokenKey: String) {
        AppPrefsUtils.putString(BaseConstant.kTokenKey, tokenKey)
    }

    fun getTokenKey(): String {
        return AppPrefsUtils.getString(BaseConstant.kTokenKey)
    }

    fun setInstanceId(instanceId: String) {
        return AppPrefsUtils.putString(BaseConstant.kInstanceId, instanceId)
    }

    fun getInstanceId(): String {
        return AppPrefsUtils.getString(BaseConstant.kInstanceId)
    }

    fun getUUID(): String {
        return UUID.randomUUID().toString()
    }

    fun getServerAddress(): String {
        return "http://" + getUserInputAddress() + "/MobileAPI/"
    }

    // 只包含IP和端口
    fun getUserInputAddress(): String {
        return AppPrefsUtils.getString(BaseConstant.kSERVER_ADDRESS, BaseConstant.DEFAULT_SERVER_ADDRESS)
    }

    fun setUserInputAddress(address: String) {
        AppPrefsUtils.putString(BaseConstant.kSERVER_ADDRESS, address)
    }

    @SuppressLint("MissingPermission")
    fun getDeviceId(context: Context, needEmpty: Boolean = false): String {
        if (needEmpty) {
            return ""
        }

        var deviceId = "UNKNOWN"

        try {
            if (selfPermissionGranted(context, Manifest.permission.READ_PHONE_STATE)) {
                var manager: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                if (manager.deviceId.isNullOrEmpty()) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        deviceId = manager.getDeviceId(0)
                    }

                } else {
                    deviceId = manager.deviceId
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            deviceId = "UNKNOWN"
        }

        return deviceId
    }

    fun selfPermissionGranted(context: Context, permission: String): Boolean {
        // For Android<Android.M, self permissions are always granted
        var result = true
        var targetSdkVersion = context.packageManager.getPackageInfo(context.packageName, 0).applicationInfo.targetSdkVersion

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                result = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
            } else {
                result = PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED
            }
        }

        return result
    }


    fun getInspectionStationState(code: String): String {
        return when (code.toInt()) {
            0 -> "未完成"

            1 -> "已完成"

            -1 -> "无任务"

            else -> "未知"

        }
    }

    /*
    完成状态：
    1:新任务
    2：待执行
    3：执行中
    4：已完成
     */
    fun getInspectionTaskState(code: String): String {
        return when (code.toInt()) {
            1 -> "新任务"

            2 -> "待执行"

            3 -> "执行中"

            4 -> "已完成"

            else -> "未知"

        }
    }
}