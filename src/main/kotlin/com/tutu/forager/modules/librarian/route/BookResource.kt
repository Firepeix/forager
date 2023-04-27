package com.tutu.forager.modules.librarian.route

import com.tutu.forager.modules.librarian.dataprovider.database.entity.Books.id
import com.tutu.forager.modules.librarian.dataprovider.dto.book.BookResponse
import com.tutu.forager.modules.librarian.handler.book.BookHandler
import com.tutu.forager.util.response.ListResponse
import com.tutu.forager.util.result.ResultHandler.Companion.handle
import io.ktor.resources.*
import io.ktor.server.resources.*
import io.ktor.server.resources.put
import io.ktor.server.routing.*

fun Route.bookRoutes(bookHandler: BookHandler) {
    get<BookResource> { resource ->
        handle(resource.getBooks(bookHandler))
    }

    put<BookResource.Id.Resume> { resource ->
        handle(resource.book.resume(bookHandler))
    }
}

@Resource("/$MODULE_PREFIX/books")
class BookResource {
    //TODO: ADD - READ / POSTPONE

    @Resource("{id}")
    class Id(val root: BookResource, private val id: String) {

        @Resource("resume")
        class Resume(val book: Id)

        suspend fun resume(handler: BookHandler): Result<Unit> {
            return handler.resumeBook(id)
        }
    }

    suspend fun getBooks(handler: BookHandler): Result<ListResponse<BookResponse>> {
        return handler.getBooks()
    }
}