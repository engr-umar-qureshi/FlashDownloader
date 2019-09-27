package com.halfinfinity.flashdownloaderlib.converters

import java.io.InputStream

interface ResourceConverter<T> {
    suspend fun convert(source: InputStream) : T
}