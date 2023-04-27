package com.tutu.forager.modules.librarian.core.exception

import io.ktor.http.*

open class BaseException(
    message: String,
    val code: String,
    val status: HttpStatusCode = HttpStatusCode.InternalServerError,
    val display: Boolean = false,
    val data: Any? = null
): Exception(message) {

}

open class NotFoundException(
    message: String,
    code: String,
    display: Boolean = false,
    data: Any? = null
): BaseException(message, code, HttpStatusCode.NotFound, display, data)