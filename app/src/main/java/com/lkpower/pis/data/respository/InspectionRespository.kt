package com.lkpower.pis.data.respository

import com.lkpower.pis.data.net.RetrofitFactory
import com.lkpower.pis.data.protocol.BaseResp
import com.lkpower.pis.ext.convert
import com.lkpower.pis.data.api.DrivingInfoApi
import com.lkpower.pis.data.api.InspectionApi
import com.lkpower.pis.data.api.LearnDocApi
import com.lkpower.pis.data.protocol.*
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Inject

class InspectionRespository @Inject constructor() {

    // 获取当前发车实例对应的站点列表
    fun getSiteList(instanceId: String, tokenKey: String): Observable<BaseResp<List<XJ_CZSL>>> {
        return RetrofitFactory.instance.create(InspectionApi::class.java).getSiteList(instanceId, tokenKey)
    }

    // 获取当前发车实例指定站点对应的巡检任务列表
    fun getXJTaskList(instanceId: String, siteId: String, tokenKey: String): Observable<BaseResp<List<MissionStateInfo>>> {
        return RetrofitFactory.instance.create(InspectionApi::class.java).getXJTaskList(instanceId, siteId, tokenKey)
    }

    // 获取巡检任务实例详细信息
    fun getXJTaskModel(taskId: String, tokenKey: String): Observable<BaseResp<MissionStateInfo>> {
        return RetrofitFactory.instance.create(InspectionApi::class.java).getXJTaskModel(taskId, tokenKey)
    }

    // 更新巡检任务状态
    fun updateMissionInfoExt(taskId: String, state:String, remark:String, tokenKey: String): Observable<BaseResp<Boolean>> {
        return RetrofitFactory.instance.create(InspectionApi::class.java).updateMissionInfoExt(taskId, state, remark, tokenKey)
    }

}