package com.tutu.forager.modules.librarian.dataprovider.dto.book

import com.tutu.forager.modules.librarian.core.domain.Library
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookResponse (
    val id: String,

    val name: String,

    @SerialName("current_chapter")
    val currentChapter: String,

    @SerialName("external_id")
    val externalId: String,

    val library: Library,

    @SerialName("ignore_until")
    val ignoreUntil: LocalDateTime?,
)