package com.tutu.forager

import com.tutu.forager.configuration.database.DatabaseFactory
import com.tutu.forager.configuration.di.DependencyContainer
import com.tutu.forager.configuration.di.create
import com.tutu.forager.modules.librarian.route.librarianRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureDatabase()
    configureResources(configureDependencies())
    configureSerialization()
}

fun Application.configureDatabase() {
    val prefix = "ktor.forager.database"

    val configuration = DatabaseFactory.DatabaseConfiguration(
        uri = environment.config.propertyOrNull("$prefix.uri")?.getString(),
        username = environment.config.propertyOrNull("$prefix.username")?.getString(),
        password = environment.config.propertyOrNull("$prefix.password")?.getString(),
        driver = environment.config.propertyOrNull("$prefix.driver")?.getString()
    )

    DatabaseFactory.configureDatabase(configuration)
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

