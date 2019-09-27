package com.halfinfinity.flashdownloaderlib.requestApi

import android.graphics.Bitmap
import com.halfinfinity.flashdownloaderlib.FlashDownloader
import com.halfinfinity.flashdownloaderlib.disposable.ImageRequestDisposable
import com.halfinfinity.flashdownloaderlib.network.NetworkError
import com.halfinfinity.flashdownloaderlib.network.NetworkResourceRequest
import com.halfinfinity.flashdownloaderlib.managers.ResourceDownloadManager
import java.util.*

class ImageRequestFinalStep(private val request: ResourceRequest) :
    ResourceRequestFinalStep {

    override fun onError(onError: (NetworkError) -> Unit): ImageRequestFinalStep {
        request.errorEventListener = onError
        return this
    }

    fun loadInto(loadInto: (Bitmap) -> Unit) : ImageRequestDisposable {
        val networkResourceRequest = NetworkResourceRequest(
            UUID.randomUUID(),
            request.url,
            loadInto,
            request.errorEventListener
        )
        ResourceDownloadManager.imageDownloadManager.processResourceRequest(networkResourceRequest)
        val disposable = ImageRequestDisposable(networkResourceRequest)
        FlashDownloader.disposeObserverMap[request.disposeObserverIdentifier]?.let {
            it.addResourceRequestDisposable(disposable)
        }
        return disposable
    }
}