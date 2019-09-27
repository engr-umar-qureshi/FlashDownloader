package com.halfinfinity.flashdownloaderlib.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream

class BitmapConverter : ResourceConverter<Bitmap> {
    override suspend fun convert(source: InputStream): Bitmap = BitmapFactory
    .decodeStream(source)
}