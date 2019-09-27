package com.halfinfinity.flashdownloaderlib.managers

import com.halfinfinity.flashdownloaderlib.cache.ResourceCacheConfiguration
import com.halfinfinity.flashdownloaderlib.converters.BitmapConverter
import com.halfinfinity.flashdownloaderlib.converters.StringConverter
import com.halfinfinity.flashdownloaderlib.converters.ResourceConverter
import com.halfinfinity.flashdownloaderlib.network.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ResourceDownloadManager<T>(private val resourceConverter: ResourceConverter<T>) {

    private val resourceCacheManager: ResourceCacheManager<T> =
        ResourceCacheManager(ResourceCacheConfiguration())
    private val resourceRequestMap = HashMap<String, ArrayList<NetworkResourceRequest<T>>>()

    private val networkClient: NetworkClient =
        OkHttpNetworkClient(
            NetworkClientConfiguration(
                10,
                10,
                10,
                TimeUnit.SECONDS
            )
        )

    private val networkClientEventHandler = object:
        NetworkClientEventHandler {
        override fun onResourceFetched(url: String, data: InputStream) {
            CoroutineScope(Default).launch {
                val resource = resourceConverter.convert(data)
                resourceCacheManager.saveResource(url, resource)
                postFetchedResource(url, resource)
            }
        }

        override fun onResourceFailed(url: String, error: NetworkError) {
            resourceRequestMap[url]?.let { requests ->
                for (request in requests) request.onError?.invoke(error)
                resourceRequestMap.remove(url)
            }
        }
    }

    suspend fun postFetchedResource(url: String, resource: T) = withContext(Main){
        resourceRequestMap[url]?.let { requests ->
            for (request in requests) request.loadInto.invoke(resource)
            resourceRequestMap.remove(url)
        }
    }

    init {
        networkClient.setEventsHandler(networkClientEventHandler)
    }

    fun processResourceRequest(resourceRequest: NetworkResourceRequest<T>){
        resourceCacheManager.retrieveResource(resourceRequest.loadFrom)?.let {
            resourceRequest.loadInto.invoke(it)
        } ?: run {
            resourceRequestMap[resourceRequest.loadFrom]?.let {
                it.add(resourceRequest)
            } ?: run {
                val requestList = ArrayList<NetworkResourceRequest<T>>()
                requestList.add(resourceRequest)
                resourceRequestMap[resourceRequest.loadFrom] = requestList
                networkClient.loadResource(resourceRequest.loadFrom)
            }
        }
    }

    fun cancelResourceRequest(url: String, requestId: UUID){
        resourceRequestMap[url]?.let { requests ->
            for(request in requests){
                if(request.id == requestId){
                    requests.remove(request)
                    break
                }
            }
            if(requests.size == 0) {
                networkClient.cancelResourceLoading(url)
                resourceRequestMap.remove(url)
            }
        }
    }

    fun stopDownloadManager(){
        networkClient.stopAllExecutingRequests()
        resourceRequestMap.clear()
    }

    companion object {
        val imageDownloadManager =
            ResourceDownloadManager(BitmapConverter())
        val jsonDownloadManager =
            ResourceDownloadManager(StringConverter())
    }
}