package com.tutu.forager.modules.librarian.configuration

import com.tutu.forager.modules.librarian.dataprovider.database.repository.BookRepository
import com.tutu.forager.modules.librarian.dataprovider.database.repository.BookRepositoryImpl
import com.tutu.forager.modules.librarian.dataprovider.gateway.BookGateway
import com.tutu.forager.modules.librarian.handler.book.BookHandler
import com.tutu.forager.util.result.ResultHandler
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Inject
import me.tatarka.inject.annotations.Provides
import com.tutu.forager.modules.librarian.core.gateway.BookGateway as BookGatewayInterface

@Component
abstract class LibrarianContainer {
    abstract val bookHandler: BookHandler

    protected val BookGateway.bind: BookGatewayInterface
        @Provides get() = this


    protected val BookRepositoryImpl.bind: BookRepository
        @Provides get() = this
}
