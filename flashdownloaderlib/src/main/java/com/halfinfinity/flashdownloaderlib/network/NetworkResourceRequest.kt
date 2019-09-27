package com.halfinfinity.flashdownloaderlib.network

import java.util.*

data class NetworkResourceRequest<T>(val id: UUID, val loadFrom: String, val loadInto: (T) -> Unit, val onError: ((NetworkError) -> Unit)?)