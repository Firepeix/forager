package com.tutu.forager.configuration.database

import com.tutu.forager.modules.librarian.dataprovider.database.entity.Books
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database.Companion.connect
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    data class DatabaseConfiguration(
        val uri: String?,
        val username: String?,
        val password: String?,
        val driver: String?
    )


    fun configureDatabase(configuration: DatabaseConfiguration) {
        val driverClassName = configuration.driver ?: "org.h2.Driver"
        val jdbcURL = "jdbc:${configuration.uri ?: "jdbc:h2:file:./build/db"}"
        val database = if (configuration.username != null) connect(jdbcURL, driverClassName, configuration.username, configuration.password!!) else connect(jdbcURL, driverClassName)

        transaction(database) {
            SchemaUtils.create(Books)
        }
    }

    suspend fun <T> query(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}