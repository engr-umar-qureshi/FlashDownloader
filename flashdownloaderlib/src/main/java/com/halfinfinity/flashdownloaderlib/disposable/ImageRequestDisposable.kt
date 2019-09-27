package com.halfinfinity.flashdownloaderlib.disposable

import android.graphics.Bitmap
import com.halfinfinity.flashdownloaderlib.network.NetworkResourceRequest
import com.halfinfinity.flashdownloaderlib.managers.ResourceDownloadManager

class ImageRequestDisposable(private val request: NetworkResourceRequest<Bitmap>):
    ResourceRequestDisposable {

    override fun cancelRequest() {
        ResourceDownloadManager.imageDownloadManager.cancelResourceRequest(request.loadFrom, request.id)
    }
}