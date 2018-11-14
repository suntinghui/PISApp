package com.lkpower.pis.service

import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.data.api.CommonApi
import com.lkpower.pis.data.api.FaultInfoApi
import com.lkpower.pis.data.protocol.*
import io.reactivex.Observable

interface FaultInfoService {

    // 故障信息查询列表
    fun getFaultInfoList(searchInfo: String, pageInfo: String, tokenKey: String): Observable<ListResult<FaultInfo>>

    // 获取故障信息详细信息
    fun getFaultInfoModel(faultId: String, tokenKey: String): Observable<FaultInfo>

    // 故障反馈
    fun addFaultInfo(tokenKey: String, faultInfo: String): Observable<CommonReturn>

    // 故障修复确认
    fun addFaultInfoConfirm(tokenKey: String, confirmInfo: String): Observable<CommonReturn>

    // 获取故障配件
    fun getFailPartList(keyword: String): Observable<List<SysDic>>

    // 获取故障类型
    fun getFaultTypeList(relParentId: String, keyword: String): Observable<List<SysDic>>

    // 获取检修类型
    fun getCheckTypeList(): Observable<List<SysDic>>

}