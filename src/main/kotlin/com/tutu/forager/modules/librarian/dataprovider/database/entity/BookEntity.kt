package com.tutu.forager.modules.librarian.dataprovider.database.entity

import com.tutu.forager.modules.librarian.core.domain.Library
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime

data class BookEntity(
    val id: String,
    val name: String,
    val currentChapter: String,
    val externalId: String,
    val library: Library,
    val ignoreUntil: LocalDateTime? = null,
)

object Books : Table() {

    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)

    val name = varchar("name", 300)
    val currentChapter = varchar("current_chapter", 50)
    val externalId = varchar("external_id", 300)
    val library = enumerationByName<Library>("library", 50)
    val ignoreUntil = datetime("ignore_until").nullable()
}