package com.halfinfinity.flashdownloaderlib.requestApi

import com.halfinfinity.flashdownloaderlib.FlashDownloader
import com.halfinfinity.flashdownloaderlib.disposable.JsonRequestDisposable
import com.halfinfinity.flashdownloaderlib.disposable.ResourceRequestDisposable
import com.halfinfinity.flashdownloaderlib.managers.ResourceDownloadManager
import com.halfinfinity.flashdownloaderlib.network.NetworkError
import com.halfinfinity.flashdownloaderlib.network.NetworkResourceRequest
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*

class JsonRequestFinalStep(private val request: JsonResourceRequest) :
    ResourceRequestFinalStep {

    override fun onError(onError: (NetworkError) -> Unit): JsonRequestFinalStep {
        request.errorEventListener = onError
        return this
    }

    fun loadJsonObjectInto(loadInto: (JSONObject) -> Unit): ResourceRequestDisposable {
        request.successJsonObjectEvent = loadInto
        return  createNetworkRequest()
    }

    fun loadJsonArrayInto(loadInto: (JSONArray) -> Unit): ResourceRequestDisposable {
        request.successJsonArrayEvent = loadInto
        return  createNetworkRequest()
    }


    private fun createNetworkRequest() : JsonRequestDisposable<String> {
        val networkResourceRequest = NetworkResourceRequest(
            UUID.randomUUID(),
            request.url,
            onLoad,
            request.errorEventListener
        )
        ResourceDownloadManager.jsonDownloadManager.processResourceRequest(networkResourceRequest)
        val disposable = JsonRequestDisposable(networkResourceRequest)
        FlashDownloader.disposeObserverMap[request.disposeObserverIdentifier]?.let {
            it.addResourceRequestDisposable(disposable)
        }
        return disposable
    }

    private val onLoad: ((String) -> Unit) = {
        request.successJsonArrayEvent?.invoke(JSONArray(JSONTokener(it)))
        request.successJsonObjectEvent?.invoke(JSONObject(JSONTokener(it)))
    }
}