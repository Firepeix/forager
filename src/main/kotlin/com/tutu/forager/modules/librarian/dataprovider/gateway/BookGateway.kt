package com.tutu.forager.modules.librarian.dataprovider.gateway

import com.tutu.forager.modules.librarian.core.domain.Book
import com.tutu.forager.modules.librarian.core.domain.Library
import com.tutu.forager.modules.librarian.core.gateway.BookGateway
import com.tutu.forager.modules.librarian.dataprovider.database.repository.BookRepository
import com.tutu.forager.modules.librarian.dataprovider.mapper.BookMapper.Companion.toBook
import com.tutu.forager.modules.librarian.dataprovider.mapper.BookMapper.Companion.toBooks
import com.tutu.forager.modules.librarian.dataprovider.mapper.BookMapper.Companion.toEntity
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import me.tatarka.inject.annotations.Inject
import java.util.UUID
import kotlin.Result.Companion.success

@Inject
class BookGateway(private val repository: BookRepository) : BookGateway {
    override suspend fun findBook(id: String): Result<Book?> {
        return repository.findBook(id).map { it?.toBook() }
    }

    override suspend fun getBooks(): Result<List<Book>> {
        return repository.getBooks().map { it.toBooks() }
    }

    override suspend fun update(book: Book): Result<Unit> {
        return repository.update(book.toEntity())
    }
}