package com.lkpower.pis.common

class BaseConstant {

    companion object {

        const val DEFAULT_SERVER_ADDRESS = "101.200.233.58:8066" // 61.235.163.12:8066
        const val kSERVER_ADDRESS = "kSERVER_ADDRESS"

        //SP表名
        const val TABLE_PREFS = "PISApp"

        const val kUsername = "kUsername"
        const val kPassword = "kPassowrd"
        const val kEmpId = "kEmpId"
        const val kTokenKey = "kTokenKey"
        const val kInstanceId = "kInstanceId"

        const val PickerImageSize = 9

        // page
        const val PageSize = 20 // 每页20条数据

        const val Att_Type_Inspection = "1" // 行车巡检任务
        const val Att_Type_Driving = "2" // 行车信息
        const val Att_Type_Other = "3" // 其他

        // FIR
        const val FIR_SERVER = "http://api.fir.im/apps/"
        const val FIR_APP_ID = "5bbf11baca87a82b96bf600a"
        const val FIR_APP_TOKEN = "b466e4ea1d74d418b79837f4fd6302a8"

        // UMENG
        const val UMENG_APPKEY = "5bd917a5f1f556bb8d0000b3"
        const val UMENG_MESSAGE_SECRET = "4cb526c50e9958b016cb90367fc39aa3"
    }
}