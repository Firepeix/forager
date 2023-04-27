package com.tutu.forager.modules.librarian.dataprovider.mapper

import com.tutu.forager.modules.librarian.core.domain.Book
import com.tutu.forager.modules.librarian.dataprovider.database.entity.BookEntity
import com.tutu.forager.modules.librarian.dataprovider.database.entity.Books
import com.tutu.forager.modules.librarian.dataprovider.dto.book.BookResponse
import com.tutu.forager.util.response.ListResponse
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.ResultRow

class BookMapper {
    companion object {
        fun List<Book>.toResponse(): ListResponse<BookResponse> {
            val response = map { it.toResponse() }
            return ListResponse(response)
        }

        private fun Book.toResponse() : BookResponse {
            return BookResponse(
                id = id,
                name = name,
                currentChapter = currentChapter,
                externalId = externalId,
                library = library,
                ignoreUntil = ignoreUntil
            )
        }

        fun List<BookEntity>.toBooks() : List<Book> {
            return map { it.toBook() }
        }

        fun BookEntity.toBook(): Book {
            return Book(
                id = id,
                name = name,
                currentChapter = currentChapter,
                externalId = externalId,
                library = library,
                ignoreUntil = ignoreUntil
            )
        }

        fun Book.toEntity(): BookEntity {
            return BookEntity(
                id = id,
                name = name,
                currentChapter = currentChapter,
                externalId = externalId,
                library = library,
                ignoreUntil = ignoreUntil
            )
        }
    }
}