package com.client.cache.sql.tables

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SQLiteArchive(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, SQLiteArchive>(ArchivesTable)
    var compressionType by ArchivesTable.compressionType
    var crc by ArchivesTable.crc
    var data by ArchivesTable.data
}