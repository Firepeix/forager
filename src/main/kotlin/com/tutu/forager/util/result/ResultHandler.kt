package com.tutu.forager.util.result

import com.tutu.forager.modules.librarian.core.exception.BaseException
import com.tutu.forager.util.response.DataResponse
import com.tutu.forager.util.response.ExceptionResponse
import io.ktor.http.*
import io.ktor.http.cio.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Inject
import java.lang.Exception

class ResultHandler {

    companion object {
        suspend inline fun <reified T: Any> PipelineContext<*, ApplicationCall>.handle(result: Result<T>) {
            result.fold(
                onSuccess = { if (it is Unit) call.respond(HttpStatusCode.OK) else call.respond(it) },
                onFailure = {
                    val (response, status) = if (it is BaseException) handleKnownException(it) else handleUnknownException(it)
                    call.respond(status, response)
                }
            )
        }

        fun handleKnownException(exception: BaseException): Pair<ExceptionResponse, HttpStatusCode> {
            return Pair(
                ExceptionResponse(
                    message = if (exception.display) exception.message else "Ops! Ocorreu um erro. Tente novamente mais tarde!",
                    code = exception.code,
                    exception = exception.parse()
                ),
                exception.status
            )
        }

        fun handleUnknownException(exception: Throwable): Pair<ExceptionResponse, HttpStatusCode> {
            return Pair(
                ExceptionResponse(
                    message = "Ops! Ocorreu um erro. Tente novamente mais tarde!",
                    code = null,
                    exception = exception.parse()
                ),
                HttpStatusCode.InternalServerError
            )
        }
    }
}