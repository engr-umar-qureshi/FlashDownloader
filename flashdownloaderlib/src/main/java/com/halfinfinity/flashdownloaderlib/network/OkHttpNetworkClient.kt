package com.halfinfinity.flashdownloaderlib.network

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class OkHttpNetworkClient(private val configs: NetworkClientConfiguration) : NetworkClient(configs) {

    private val networkJobsMap: HashMap<String, Job> = HashMap()

    private val mOkHttpClient: OkHttpClient = with(OkHttpClient.Builder()) {
        connectTimeout(configs.connectionTimeout, configs.timeUnit)
        readTimeout(configs.readTimeout, configs.timeUnit)
        writeTimeout(configs.writeTimeout, configs.timeUnit)
        build()
    }

    override  fun loadResource(url: String) {
        val resourceRequest = Request.Builder().url(url).build()
        val networkCall = mOkHttpClient.newCall(resourceRequest)
        networkJobsMap[url] = CoroutineScope(IO).launch {
            getResourceAsync(networkCall)
        }
    }

    override fun cancelResourceLoading(url: String) {
        networkJobsMap[url]?.cancel()
        networkJobsMap.remove(url)
    }

    override fun stopAllExecutingRequests() {
        for (entry in networkJobsMap.entries) {
            networkJobsMap[entry.key]?.cancel()
        }
        networkJobsMap.clear()
    }

    private suspend fun getResourceAsync(networkCall: Call) {
        postResultViaEventHandler(networkCall.request().url().toString(), networkCall.execute())
    }

    private suspend fun postResultViaEventHandler(url: String, response: Response) =  withContext(Main){
        if (response.isSuccessful) {
            response.body().toString()
            response.body()?.let {
                eventHandler?.onResourceFetched(url,it.byteStream())
            }
        } else {
            eventHandler?.onResourceFailed(url,
                NetworkError(
                    response.code(),
                    response.message()
                )
            )
        }
    }
}