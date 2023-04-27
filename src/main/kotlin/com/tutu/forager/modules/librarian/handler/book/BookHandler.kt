package com.tutu.forager.modules.librarian.handler.book

import com.tutu.forager.modules.librarian.core.domain.Book
import com.tutu.forager.modules.librarian.core.exception.BookNotFoundException
import com.tutu.forager.modules.librarian.core.usecase.book.BookUseCase
import com.tutu.forager.modules.librarian.core.usecase.book.ResumeBookUseCase
import com.tutu.forager.modules.librarian.dataprovider.dto.book.BookResponse
import com.tutu.forager.modules.librarian.dataprovider.dto.book.CreateBookRequest
import com.tutu.forager.modules.librarian.dataprovider.mapper.BookMapper.Companion.toResponse
import com.tutu.forager.util.response.ListResponse
import com.tutu.forager.util.result.filterNull
import com.tutu.forager.util.result.then
import me.tatarka.inject.annotations.Inject

@Inject
class BookHandler(private val useCase: BookUseCase, private val resumeUseCase: ResumeBookUseCase) {

    suspend fun createBook(request: CreateBookRequest): Result<Unit> {
        return useCase.createBook(request)
    }

    suspend fun getBooks(): Result<ListResponse<BookResponse>> {
        return useCase.getBooks().map { it.toResponse() }
    }

    suspend fun resumeBook(id: String): Result<Unit> {
        return useCase.findBook(id).filterNull<Book>(BookNotFoundException(id)).then {
            resumeUseCase.resume(it)
        }
    }

    suspend fun deleteBook(id: String): Result<Unit> {
        return useCase.deleteBook(id)
    }
}