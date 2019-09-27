package com.halfinfinity.pintrestapi.network.models

import android.os.Parcel
import android.os.Parcelable
import com.halfinfinity.pintrestapi.network.responseModels.PinboardItemResponse


data class PinboardItem(
    val id: String,
    val color: String,
    val likes: Int,
    val urlRegular: String,
    val urlFull: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(color)
        writeInt(likes)
        writeString(urlRegular)
        writeString(urlFull)
    }

    companion object {
        fun convert(responseItem: PinboardItemResponse): PinboardItem {
            return PinboardItem(
                responseItem.id,
                responseItem.color,
                responseItem.likes,
                responseItem.urls.urlRegular,
                responseItem.urls.urlFull
            )
        }

        fun convert(responseItemList: ArrayList<PinboardItemResponse>): ArrayList<PinboardItem> {
            val list = ArrayList<PinboardItem>()
            for(responseItem in responseItemList){
                list.add(convert(responseItem))
            }
            return list
        }

        @JvmField
        val CREATOR: Parcelable.Creator<PinboardItem> = object : Parcelable.Creator<PinboardItem> {
            override fun createFromParcel(source: Parcel): PinboardItem = PinboardItem(source)
            override fun newArray(size: Int): Array<PinboardItem?> = arrayOfNulls(size)
        }
    }
}