package com.tutu.forager.modules.librarian.dataprovider.dto.book

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostponeBookRequest (
    @SerialName("ignore_until")
    val ignoreUntil: LocalDateTime? = null,

    @SerialName("delay")
    val delay: String? = null,
)