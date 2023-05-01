package com.tutu.forager.modules.librarian.route

import com.tutu.forager.modules.librarian.dataprovider.dto.book.BookResponse
import com.tutu.forager.modules.librarian.dataprovider.dto.book.CreateBookRequest
import com.tutu.forager.modules.librarian.dataprovider.dto.book.PostponeBookRequest
import com.tutu.forager.modules.librarian.handler.book.BookHandler
import com.tutu.forager.util.response.ListResponse
import com.tutu.forager.util.result.ResultHandler.Companion.handle
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.routing.Route

fun Route.bookRoutes(bookHandler: BookHandler) {
    get<BookResource> { resource ->
        handle(resource.getBooks(bookHandler))
    }

    post<BookResource> { resource ->
        handle(resource.createBook(bookHandler, call.receive()))
    }

    delete<BookResource.Id> { resource ->
        handle(resource.delete(bookHandler))
    }

    put<BookResource.Id.Resume> { resource ->
        handle(resource.book.resume(bookHandler))
    }

    put<BookResource.Id.Postpone> { resource ->
        handle(resource.book.postpone(bookHandler, call.receive()))
    }
}

@Resource("/$MODULE_PREFIX/books")
class BookResource {
    //TODO: ADD - READ / POSTPONE

    @Resource("{id}")
    class Id(val root: BookResource, private val id: String) {

        @Resource("resume")
        class Resume(val book: Id)

        @Resource("postpone")
        class Postpone(val book: Id)

        suspend fun postpone(handler: BookHandler, request: PostponeBookRequest): Result<Unit> {
            return handler.postponeBook(id, request)
        }

        suspend fun resume(handler: BookHandler): Result<Unit> {
            return handler.resumeBook(id)
        }

        suspend fun delete(handler: BookHandler): Result<Unit> {
            return handler.deleteBook(id)
        }
    }

    suspend fun getBooks(handler: BookHandler): Result<ListResponse<BookResponse>> {
        return handler.getBooks()
    }

    suspend fun createBook(handler: BookHandler, request: CreateBookRequest): Result<Unit> {
        return handler.createBook(request)
    }
}