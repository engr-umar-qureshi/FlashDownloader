package com.halfinfinity.pintrestapi.network.interfaces

import com.halfinfinity.pintrestapi.network.responseModels.PinboardItemResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface PinboardApiInterface {

    @GET("raw/wgkJgazE")
    fun getPinboardItems(): Observable<ArrayList<PinboardItemResponse>>
}