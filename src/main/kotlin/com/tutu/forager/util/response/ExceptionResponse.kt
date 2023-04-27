package com.tutu.forager.util.response

import io.ktor.http.cio.*
import kotlinx.serialization.Serializable

@Serializable
data class ExceptionResponse(
    val message: String?,
    val code: String?,
    val exception: String,
    val data: Map<String, String?>? = null
)