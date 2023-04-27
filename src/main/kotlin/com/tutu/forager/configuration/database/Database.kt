package com.tutu.forager.configuration.database

import com.tutu.forager.modules.librarian.dataprovider.database.entity.Books
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database as ExposedDatabase

object DatabaseFactory {
    fun configureDatabase() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"
        val database = ExposedDatabase.connect(jdbcURL, driverClassName)

        transaction(database) {
            SchemaUtils.create(Books)
        }
    }

    suspend fun <T> query(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}