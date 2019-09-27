package com.halfinfinity.flashdownloaderlib.requestApi

import com.halfinfinity.flashdownloaderlib.network.NetworkError

open class ResourceRequest(val disposeObserverIdentifier: String, var url: String, var errorEventListener: ((NetworkError) -> Unit)? = null)