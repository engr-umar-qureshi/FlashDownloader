package com.halfinfinity.pintrestapi.network.responseModels

import com.google.gson.annotations.SerializedName

data class PinboardItemUrlsResponse(
    @SerializedName("regular")
    val urlRegular: String,
    @SerializedName("full")
    val urlFull: String)