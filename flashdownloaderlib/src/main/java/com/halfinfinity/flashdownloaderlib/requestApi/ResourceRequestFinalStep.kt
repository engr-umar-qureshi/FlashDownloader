package com.halfinfinity.flashdownloaderlib.requestApi

import com.halfinfinity.flashdownloaderlib.network.NetworkError

interface ResourceRequestFinalStep {
    fun onError(onError: (NetworkError) -> Unit) : ResourceRequestFinalStep
}