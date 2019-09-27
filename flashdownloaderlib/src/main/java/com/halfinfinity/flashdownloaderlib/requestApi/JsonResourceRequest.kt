package com.halfinfinity.flashdownloaderlib.requestApi

import com.halfinfinity.flashdownloaderlib.network.NetworkError
import org.json.JSONArray
import org.json.JSONObject

class JsonResourceRequest(disposeObserverIdentifier: String, url: String, errorEventListener: ((NetworkError) -> Unit)? = null, var successJsonObjectEvent: ((JSONObject) -> Unit)? = null, var successJsonArrayEvent: ((JSONArray) -> Unit)? = null) : ResourceRequest(disposeObserverIdentifier, url, errorEventListener)