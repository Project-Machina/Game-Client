package com.client.cache.sql

import com.client.cache.Archive
import com.client.cache.compression.Compression
import com.client.cache.sql.tables.ArchivesTable
import com.client.cache.sql.tables.SQLiteArchive
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.ByteArrayOutputStream
import java.nio.file.Path
import java.util.zip.CRC32
import java.util.zip.GZIPOutputStream
import kotlin.io.path.absolutePathString

class SQLiteIndex(path: Path) {
    private val db: Database = Database.connect("jdbc:sqlite:${path.absolutePathString()}", driver = "org.sqlite.JDBC")

    init {
        transaction(db) {
            if (!ArchivesTable.exists()) {
                SchemaUtils.createMissingTablesAndColumns(ArchivesTable)
            }
            //SchemaUtils.addMissingColumnsStatements(ArchivesTable)
        }
    }

    fun getArchive(id: Int): Archive? {
        return transaction(db) {
            val data = SQLiteArchive.findById(id)
            if (data != null) {
                val compType = Compression.valueOf(data.compressionType)
                Archive(data.id.value, data.crc, data.data.bytes, compType)
            } else null
        }
    }

    fun putArchive(archive: Archive) {
        val crc32 = CRC32()
        transaction<Unit>(db) {
            val sqlArchive = SQLiteArchive.findById(archive.id)
            if (sqlArchive == null) {
                SQLiteArchive.new(archive.id) {
                    crc32.update(archive.rawData)
                    crc = crc32.value
                    compressionType = archive.compression.name
                    data = ExposedBlob(compressData(archive.compression, archive.rawData))
                }
            } else {
                crc32.update(archive.rawData)
                if (crc32.value != sqlArchive.crc) {
                    sqlArchive.crc = crc32.value
                    sqlArchive.compressionType = archive.compression.name
                    sqlArchive.data = ExposedBlob(compressData(archive.compression, archive.rawData))
                }
            }
        }
    }

    fun compressData(comp: Compression, data: ByteArray): ByteArray {
        return when (comp) {
            Compression.GZIP -> {
                val baos = ByteArrayOutputStream()
                val gis = GZIPOutputStream(baos)
                gis.write(data)
                gis.finish()
                baos.toByteArray()
            }
            Compression.NONE -> data
        }
    }
}