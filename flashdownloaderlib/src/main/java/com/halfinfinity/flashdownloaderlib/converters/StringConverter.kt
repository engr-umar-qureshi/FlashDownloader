package com.halfinfinity.flashdownloaderlib.converters

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class StringConverter: ResourceConverter<String> {
    override suspend fun convert(source: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(source))
        val total = StringBuilder()
        var line = bufferedReader.readLine()
        while (line != null) {
            total.append(line).append('\n')
            line = bufferedReader.readLine()
        }
        return  total.toString()
    }

}