package com.halfinfinity.pintrestapi.controller

import androidx.lifecycle.MutableLiveData
import com.halfinfinity.pintrestapi.network.interfaces.PinboardApiInterface
import com.halfinfinity.pintrestapi.network.models.PinboardItem
import com.halfinfinity.pintrestapi.network.responseModels.PinboardItemResponse
import com.halfinfinity.pintrestapi.network.util.ApiError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class PinboardApi(retrofitClient: Retrofit, private val liveDataError: MutableLiveData<ApiError>?) {

    private val mPinboardDataSource: PinboardApiInterface = retrofitClient.create(PinboardApiInterface::class.java)

    public fun getPinboardItems(liveDataPinboard: MutableLiveData<ArrayList<PinboardItem>>){
        val observable = mPinboardDataSource.getPinboardItems()

        val consumer = Consumer<ArrayList<PinboardItemResponse>>{liveDataPinboard.setValue(PinboardItem.convert(it))}

        val errorHandler = Consumer<Throwable> { throwable ->
            if (liveDataError != null) {
                liveDataError.value = ApiError.translateFailureToApiError(throwable)
            }
        }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer, errorHandler);

    }
}