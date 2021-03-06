package com.lkpower.pis.data.net

import com.lkpower.pis.common.BaseConstant
import com.lkpower.pis.utils.PISUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
    Retrofit工厂，单例
 */
class RetrofitFactory private constructor() {

    /*
        单例实现
     */
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val interceptor: Interceptor
    private var retrofit: Retrofit

    //初始化
    init {
        //通用拦截
        interceptor = Interceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .build()

            chain.proceed(request)
        }

        //Retrofit实例化
        retrofit = Retrofit.Builder()
                .baseUrl(PISUtil.getServerAddress())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()
    }

    /*
        OKHttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(initLogInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    /*
        日志拦截器
     */
    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /*
        具体服务实例化
     */
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    // 为了能够实现更换服务器地址后能够立即生效。
    // 应该不是最好的实现方式。
    fun resetRetrofit() {
        retrofit = Retrofit.Builder()
                .baseUrl(PISUtil.getServerAddress())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()
    }
}