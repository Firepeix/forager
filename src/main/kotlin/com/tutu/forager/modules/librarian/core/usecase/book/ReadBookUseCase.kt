package com.tutu.forager.modules.librarian.core.usecase.book

import com.tutu.forager.modules.librarian.core.domain.Book
import com.tutu.forager.modules.librarian.core.domain.Chapter
import com.tutu.forager.modules.librarian.core.gateway.BookGateway
import me.tatarka.inject.annotations.Inject

@Inject
class ReadBookUseCase(private val gateway: BookGateway) {

    suspend fun read(book: Book, chapter: String?): Result<Book> {
        return gateway.update(book.copy(currentChapter = chapter ?: increment(book.currentChapter)))
    }

    private fun increment(chapter: Chapter): Chapter {
        val hasComma = chapter.contains(",")
        val current = chapter.replace(",", ".")
        val hasDot = chapter.contains(".")

        return when {
            hasDot -> incrementFloat(current)
            hasComma -> incrementFloat(current).replace(".", ",")
            else -> "${current.toInt().inc()}"
        }
    }

    private fun incrementFloat(chapter: String): String {
        val (lhs, rhs) = chapter.split(".")

        return "$lhs.${(rhs.toIntOrNull() ?: 0).inc()}"
    }
}