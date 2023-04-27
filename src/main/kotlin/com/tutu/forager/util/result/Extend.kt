package com.tutu.forager.util.result

import com.tutu.forager.modules.librarian.core.exception.BaseException

fun Throwable.parse(): String {
    val details = "${this::class} @ ${stackTrace[0]}"
    return message?.run { "$this - $details" } ?: details
}

fun Throwable.context(dataName: String? = null): Map<String, Any?> {
    val name = dataName ?: "data"
    return mapOf("exception" to parse())
}

//fun <T> Result<T>.httpCode(successCode: Int): Int {
//    val exception = exceptionOrNull()
//
//    return when {
//        isSuccess -> sucessCode
//        exception == null -> sucessCode
//        exception is BaseException -> exception.httpErrorCode
//        else -> INTERNAL_ERROR_CODE
//    }
//}

inline fun <R, reified T> Result<T>.then(transform: (value: T) -> Result<R>): Result<R> {
    return when {
        isSuccess -> transform(getOrNull() as T)
        else -> Result.failure(exceptionOrNull()!!)
    }
}

inline fun <reified R: Any> Result<Any?>.filterNull(exception: BaseException): Result<R> {
    return then { value ->
        value.take(
            some = { Result.success(it as R) },
            none = { Result.failure(exception)}
        )
    }
}

inline fun <T: Any?, R> T?.take(some: (T) -> R, none: () -> R): R {
    if (this != null) {
        return some(this)
    }

    return none()
}

/**
 * Transforma um número variavel de results em um unico
 * result sendo uma lista
 */
fun <T, R: Result<T>> Result.Companion.join(vararg results: R): Result<List<T>> {
    val result = mutableListOf<T>()
    results.forEach {
        if (it.isFailure) {
            return failure(it.exceptionOrNull()!!)
        }

        result.add(it.getOrNull()!!)
    }

    return success(result)
}