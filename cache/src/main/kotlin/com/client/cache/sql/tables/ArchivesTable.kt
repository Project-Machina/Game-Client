package com.client.cache.sql.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object ArchivesTable : IdTable<Int>("Archives") {
    override val id: Column<EntityID<Int>> = integer("id").uniqueIndex().entityId()
    val compressionType = text("compression").default("none")
    val crc = long("crc").default(0)
    val data = blob("data")
}