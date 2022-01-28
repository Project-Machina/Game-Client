package com.client.cache

import com.client.cache.compression.Compression
import java.io.ByteArrayInputStream
import java.util.zip.GZIPInputStream

data class Archive(val id: Int, val crc: Long, val rawData: ByteArray, val compression: Compression = Compression.NONE) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Archive

        if (id != other.id) return false
        if (!rawData.contentEquals(other.rawData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + rawData.contentHashCode()
        return result
    }
    companion object {
        val Archive.data get() = decompress(compression, rawData)
        fun decompress(comp: Compression, data: ByteArray) : ByteArray {
            return when(comp) {
                Compression.GZIP -> {
                    val bais = ByteArrayInputStream(data)
                    val gos = GZIPInputStream(bais)
                    gos.readBytes()
                }
                Compression.NONE -> data
            }
        }
    }
}