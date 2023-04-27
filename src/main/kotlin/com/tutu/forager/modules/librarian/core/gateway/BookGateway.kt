package com.tutu.forager.modules.librarian.core.gateway

import com.tutu.forager.modules.librarian.core.domain.Book

interface BookGateway {

    suspend fun findBook(id: String): Result<Book?>

    suspend fun getBooks(): Result<List<Book>>

    suspend fun insert(book: Book): Result<Unit>

    suspend fun update(book: Book): Result<Unit>

    suspend fun delete(id: String): Result<Unit>
}