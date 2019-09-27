package com.halfinfinity.flashdownloaderlib.network

import java.util.concurrent.TimeUnit

data class NetworkClientConfiguration(var connectionTimeout: Long, val readTimeout: Long, val writeTimeout: Long, val timeUnit: TimeUnit, val listCustomConfigs: List<NetworkClientCustomConfiguration>? = null)
