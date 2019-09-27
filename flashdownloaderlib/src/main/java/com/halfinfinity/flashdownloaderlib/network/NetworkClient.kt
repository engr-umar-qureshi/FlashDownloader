package com.halfinfinity.flashdownloaderlib.network

abstract class NetworkClient(private val configs: NetworkClientConfiguration) {

    protected var eventHandler: NetworkClientEventHandler? = null

    abstract fun loadResource(url: String)

    abstract fun cancelResourceLoading(url: String)

    abstract fun stopAllExecutingRequests()

    fun setEventsHandler(handler: NetworkClientEventHandler) {
        eventHandler = handler
    }
}