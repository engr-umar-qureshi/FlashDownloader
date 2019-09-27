package com.halfinfinity.flashdownloaderlib.disposable

import com.halfinfinity.flashdownloaderlib.network.NetworkResourceRequest
import com.halfinfinity.flashdownloaderlib.managers.ResourceDownloadManager

class JsonRequestDisposable<T>(private val request: NetworkResourceRequest<T>):
    ResourceRequestDisposable {
    override fun cancelRequest() {
        ResourceDownloadManager.imageDownloadManager.cancelResourceRequest(request.loadFrom, request.id)
    }
}