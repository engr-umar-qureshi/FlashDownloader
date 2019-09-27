package com.halfinfinity.flashdownloaderlib.cache

import com.halfinfinity.flashdownloaderlib.cache.ResourceCacheConfiguration.ConfigurationConstants.DEFAULT_CACHE_LIMIT

data class ResourceCacheConfiguration(val cacheLimit: Int = DEFAULT_CACHE_LIMIT){
    object ConfigurationConstants {
        const val DEFAULT_CACHE_LIMIT = 256
    }
}