package com.tutu.forager.modules.librarian.handler.book

import com.tutu.forager.modules.librarian.core.domain.Book
import com.tutu.forager.modules.librarian.core.exception.BookNotFoundException
import com.tutu.forager.modules.librarian.core.usecase.book.BookUseCase
import com.tutu.forager.modules.librarian.core.usecase.book.PostponeBookUseCase
import com.tutu.forager.modules.librarian.core.usecase.book.PostponeBookUseCase.Delay
import com.tutu.forager.modules.librarian.core.usecase.book.ReadBookUseCase
import com.tutu.forager.modules.librarian.core.usecase.book.ResumeBookUseCase
import com.tutu.forager.modules.librarian.dataprovider.dto.book.BookResponse
import com.tutu.forager.modules.librarian.dataprovider.dto.book.CreateBookRequest
import com.tutu.forager.modules.librarian.dataprovider.dto.book.PostponeBookRequest
import com.tutu.forager.modules.librarian.dataprovider.dto.book.ReadBookRequest
import com.tutu.forager.modules.librarian.dataprovider.mapper.BookMapper.Companion.toResponse
import com.tutu.forager.util.response.ListResponse
import com.tutu.forager.util.result.reportNull
import com.tutu.forager.util.result.then
import me.tatarka.inject.annotations.Inject

@Inject
class BookHandler(
    private val useCase: BookUseCase,
    private val resumeUseCase: ResumeBookUseCase,
    private val postponeUseCase: PostponeBookUseCase,
    private val readBookUseCase: ReadBookUseCase,
) {

    suspend fun createBook(request: CreateBookRequest): Result<Unit> {
        return useCase.createBook(request)
    }

    suspend fun getBooks(): Result<ListResponse<BookResponse>> {
        return useCase.getBooks().map { it.toResponse() }
    }

    suspend fun resumeBook(id: String): Result<Book> {
        return useCase.findBook(id).reportNull(BookNotFoundException(id)).then {
            resumeUseCase.resume(it)
        }
    }

    suspend fun postponeBook(id: String, request: PostponeBookRequest): Result<Book> {
        return Delay.parse(request.delay).then  { delay ->
            useCase.findBook(id).reportNull(BookNotFoundException(id)).map { Pair(delay, it) }
        }.then { (delay: Delay?, book: Book) ->
            postponeUseCase.postpone(book, request.ignoreUntil, delay)
        }
    }

    suspend fun readBook(id: String, request: ReadBookRequest): Result<Book> {
        return useCase.findBook(id).reportNull(BookNotFoundException(id)).then {
            readBookUseCase.read(it, request.chapter)
        }
    }

    suspend fun deleteBook(id: String): Result<Unit> {
        return useCase.deleteBook(id)
    }
}