package com.halfinfinity.pintrestapi

import androidx.lifecycle.MutableLiveData
import com.halfinfinity.pintrestapi.controller.PinboardApi
import com.halfinfinity.pintrestapi.network.util.ApiError
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

public class PintrestApiClient {

    fun getPinboardtApi(liveDataError: MutableLiveData<ApiError>): PinboardApi {
        return PinboardApi(mRetrofitClient, liveDataError)
    }

    companion object{

        private const val CONNECTION_TIMEOUT_SECONDS = 15
        private const val READ_TIMEOUT_SECONDS = 30
        private const val WRITE_TIMEOUT_SECONDS = 30

        val instance = PintrestApiClient()

        private val mRetrofitClient = Retrofit.Builder()
            .baseUrl(BuildConfig.DEFAULT_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        fun getOkHttpClient(): OkHttpClient {

            return OkHttpClient()
                .newBuilder()
                .connectTimeout(CONNECTION_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .build()
        }
    }
}