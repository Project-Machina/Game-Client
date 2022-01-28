package com.client.cache.sql

import com.client.cache.Archive
import com.client.cache.Archive.Companion.data
import com.client.cache.compression.Compression
import java.nio.file.Path
import kotlin.io.path.exists

class SQLiteCache(val path: Path) {
    private val indices = mutableMapOf<Int, SQLiteIndex>()

    init {
        repeat(255) {
            val p = path.resolve("cache-$it.pmcache")
            if(p.exists()) {
                indices[it] = SQLiteIndex(p)
            }
        }
    }

    fun raw(id: Int, archiveId: Int) : ByteArray {
        val index = index(id)
        val archive = index.getArchive(archiveId)
        if(archive != null) {
            return archive.rawData
        }
        return byteArrayOf()
    }

    fun data(id: Int, archiveId: Int) : ByteArray {
        val index = index(id)
        val archive = index.getArchive(archiveId)
        if(archive != null) {
            return archive.data
        }
        return byteArrayOf()
    }

    fun put(id: Int, archiveId: Int, data: ByteArray, compression: Compression = Compression.NONE) : Boolean {
        val index = index(id)
        index.putArchive(Archive(archiveId, 0L, data, compression))
        return true
    }

    fun index(id: Int) : SQLiteIndex {
        return indices[id] ?: error("Index not found.")
    }
}