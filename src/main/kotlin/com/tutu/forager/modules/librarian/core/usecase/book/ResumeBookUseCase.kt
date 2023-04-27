package com.tutu.forager.modules.librarian.core.usecase.book

import com.tutu.forager.modules.librarian.core.domain.Book
import com.tutu.forager.modules.librarian.core.gateway.BookGateway
import me.tatarka.inject.annotations.Inject

@Inject
class ResumeBookUseCase(private val gateway: BookGateway) {

    suspend fun resume(book: Book): Result<Unit> {
        return gateway.update(book.copy(ignoreUntil = null))
    }
}