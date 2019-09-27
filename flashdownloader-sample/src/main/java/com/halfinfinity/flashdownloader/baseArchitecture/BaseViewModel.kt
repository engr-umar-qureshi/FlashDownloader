package com.halfinfinity.flashdownloader.baseArchitecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halfinfinity.pintrestapi.network.util.ApiError

open class BaseViewModel: ViewModel() {

    protected var mApiError: MutableLiveData<ApiError> = MutableLiveData()

    fun getLiveError(): LiveData<ApiError> {
        return mApiError
    }
}