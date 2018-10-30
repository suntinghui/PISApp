package com.lkpower.base.common

class BaseConstant {

    companion object {

        const val SERVER_ADDRESS = "http://101.200.233.58:8066/MobileAPI/"

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
        const val UMENG_APPKEY = "580d95c6c895761f96000494"
        const val UMENG_MESSAGE_SECRET = "36608f6621ffe5f61f79554a5fa7cd46"
    }
}