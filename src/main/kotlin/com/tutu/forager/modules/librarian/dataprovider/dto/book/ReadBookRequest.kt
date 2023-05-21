package com.tutu.forager.modules.librarian.dataprovider.dto.book

import com.tutu.forager.modules.librarian.core.domain.Chapter
import kotlinx.serialization.Serializable

@Serializable
data class ReadBookRequest (
    val chapter: Chapter? = null,
)