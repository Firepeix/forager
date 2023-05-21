package com.tutu.forager.modules.librarian.dataprovider.gateway

import com.tutu.forager.modules.librarian.core.domain.Book
import com.tutu.forager.modules.librarian.core.gateway.BookGateway
import com.tutu.forager.modules.librarian.dataprovider.database.repository.BookRepository
import com.tutu.forager.modules.librarian.dataprovider.mapper.BookMapper.Companion.toBook
import com.tutu.forager.modules.librarian.dataprovider.mapper.BookMapper.Companion.toBooks
import com.tutu.forager.modules.librarian.dataprovider.mapper.BookMapper.Companion.toEntity
import com.tutu.forager.modules.librarian.dataprovider.mapper.BookMapper.Companion.toTransientBook
import me.tatarka.inject.annotations.Inject

@Inject
class BookGateway(private val repository: BookRepository) : BookGateway {
    override suspend fun findBook(id: String): Result<Book?> {
        return repository.findBook(id).map { it?.toBook() }
    }

    override suspend fun getBooks(): Result<List<Book>> {
        return repository.getBooks().map { it.toBooks() }
    }

    override suspend fun insert(book: Book): Result<Unit> {
        return repository.insert(book.toTransientBook())
    }

    override suspend fun update(model: Book): Result<Book> {
        return repository.update(model.toEntity()).map { it.toBook() }
    }

    override suspend fun delete(id: String): Result<Unit> {
        return repository.delete(id)
    }
}