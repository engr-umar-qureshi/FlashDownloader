package com.halfinfinity.flashdownloader.ui.pinboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.halfinfinity.flashdownloader.baseArchitecture.BaseViewModel
import com.halfinfinity.pintrestapi.PintrestApiClient
import com.halfinfinity.pintrestapi.controller.PinboardApi
import com.halfinfinity.pintrestapi.network.models.PinboardItem

class PinboardViewModel : BaseViewModel() {

    private var mListPinboardItems: MutableLiveData<ArrayList<PinboardItem>> = MutableLiveData()
    private var mPinboardApi: PinboardApi = PintrestApiClient.instance.getPinboardtApi(mApiError)

    fun getPinboardItems(): LiveData<ArrayList<PinboardItem>> {
        mPinboardApi.getPinboardItems(mListPinboardItems)
        return mListPinboardItems
    }

}
