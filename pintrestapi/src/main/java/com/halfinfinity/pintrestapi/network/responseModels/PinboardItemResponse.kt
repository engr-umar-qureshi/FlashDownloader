package com.halfinfinity.pintrestapi.network.responseModels

import com.google.gson.annotations.SerializedName

data class PinboardItemResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("urls")
    val urls: PinboardItemUrlsResponse
    )