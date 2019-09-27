package com.halfinfinity.flashdownloaderlib.network

import java.io.InputStream

interface NetworkClientEventHandler {
        fun onResourceFetched(url: String, data: InputStream)
        fun onResourceFailed(url: String, error: NetworkError)
}