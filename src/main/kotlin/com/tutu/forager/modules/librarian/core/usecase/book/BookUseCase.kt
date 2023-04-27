package com.tutu.forager.modules.librarian.core.usecase.book

import com.tutu.forager.modules.librarian.core.domain.Book
import com.tutu.forager.modules.librarian.core.domain.Library
import com.tutu.forager.modules.librarian.core.gateway.BookGateway
import com.tutu.forager.modules.librarian.dataprovider.dto.book.CreateBookRequest
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import me.tatarka.inject.annotations.Inject
import java.util.UUID

@Inject
class BookUseCase(private val gateway: BookGateway) {

    suspend fun findBook(id: String): Result<Book?> {
        return gateway.findBook(id)
    }

    suspend fun getBooks(): Result<List<Book>> {
        return gateway.getBooks()
    }

    suspend fun createBook(request: CreateBookRequest): Result<Unit> {
        val book = Book(
            name = request.name,
            currentChapter = request.currentChapter,
            externalId = request.externalId,
            library = request.library,
        )

        return gateway.insert(book)
    }

    suspend fun deleteBook(id: String): Result<Unit> {
        return gateway.delete(id)
    }
}