package com.tutu.forager

import com.tutu.forager.configuration.database.DatabaseFactory.configureDatabase
import com.tutu.forager.configuration.di.DependencyContainer
import com.tutu.forager.configuration.di.create
import com.tutu.forager.modules.librarian.route.BookResource
import com.tutu.forager.modules.librarian.route.librarianRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureDatabase()
    configureResources(configureDependencies())
    configureSerialization()
}

fun Application.configureResources(container: DependencyContainer) {
    install(Resources)

    librarianRoutes(container.librarianContainer)
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

fun configureDependencies(): DependencyContainer {
    return DependencyContainer::class.create()
}