package com.lkpower.base.data.protocol

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface AttApi {

    // 文件上传
    @Multipart
    @POST("ATTManager.ashx?Commond=UploadMultipart")
    fun uploadFiles(@Part("busId") busId: RequestBody,
                    @Part("ATTBusModule") attType: RequestBody,
                    @Part("tokenKey") tokenKey: RequestBody,
                    @Part file: MultipartBody.Part): Observable<BaseResp<List<AttModel>>>
}