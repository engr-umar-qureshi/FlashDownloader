package com.halfinfinity.flashdownloaderlib.managers

import android.util.LruCache
import com.halfinfinity.flashdownloaderlib.cache.ResourceCacheConfiguration

class ResourceCacheManager<T>(cacheConfig: ResourceCacheConfiguration) {

    private val lruCache: LruCache<String, T>
            = LruCache(cacheConfig.cacheLimit * 1024 * 1024)

    fun retrieveResource(resourceUrl: String): T = lruCache.get(resourceUrl)

    fun saveResource(url: String, resource: T) {
        lruCache.put(url, resource)
    }

}