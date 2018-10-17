package com.lkpower.pis.service

import com.lkpower.pis.data.protocol.OutCheckInInfo
import com.lkpower.pis.data.protocol.UserInfo
import com.lkpower.pis.data.protocol.XJ_LCFCInfo
import io.reactivex.Observable

interface TasktanceService {

    // 获取出乘报到任务列表
    fun getOutCheckInList(instanceId: String, tokenKey: String): Observable<List<OutCheckInInfo>>

    /**
     * 获取出乘报到任务实例
     */
    fun getOutCheckInDetail(instanceId: String, taskId: String, tokenKey: String): Observable<OutCheckInInfo>

    /*
      出乘报到
     */
    fun setOutCheckIn(taskId: String, tokenKey: String): Observable<String>
}