package com.tutu.forager.modules.librarian.dataprovider.database.repository

import com.tutu.forager.configuration.database.DatabaseFactory.query
import com.tutu.forager.modules.librarian.dataprovider.database.entity.BookEntity
import com.tutu.forager.modules.librarian.dataprovider.database.entity.Books
import com.tutu.forager.modules.librarian.dataprovider.database.entity.Books.id
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import me.tatarka.inject.annotations.Inject
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

interface BookRepository {
    suspend fun findBook(id: String): Result<BookEntity?>

    suspend fun getBooks(): Result<List<BookEntity>>

    suspend fun update(entity: BookEntity): Result<Unit>
}

@Inject
class BookRepositoryImpl : BookRepository {
    override suspend fun findBook(id: String): Result<BookEntity?> = query {
        runCatching { Books.select { Books.id eq id.toInt() }.singleOrNull()?.run(::createBookEntity) }
    }

    override suspend fun getBooks(): Result<List<BookEntity>> = query {
        runCatching { Books.selectAll().map(::createBookEntity) }
    }

    override suspend fun update(entity: BookEntity): Result<Unit> = query {
        runCatching {
            Books.update (where = { id eq entity.id.toInt() }) {
                it[currentChapter] = entity.currentChapter
                it[ignoreUntil] = entity.ignoreUntil?.toJavaLocalDateTime()
            }

            Unit
        }
    }

    private fun createBookEntity(row: ResultRow): BookEntity {
        return BookEntity(
            id = row[id].toString(),
            name = row[Books.name],
            currentChapter = row[Books.currentChapter],
            externalId = row[Books.externalId],
            library = row[Books.library],
            ignoreUntil = row[Books.ignoreUntil]?.toKotlinLocalDateTime()
        )
    }
}