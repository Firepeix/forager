package com.tutu.forager.modules.librarian.route

import com.tutu.forager.modules.librarian.configuration.LibrarianContainer
import io.ktor.server.application.*
import io.ktor.server.routing.*

const val MODULE_PREFIX = "librarian"

fun Application.librarianRoutes(container: LibrarianContainer) {
    routing {
        bookRoutes(container.bookHandler)
    }
}