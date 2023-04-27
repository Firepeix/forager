package com.tutu.forager.modules.librarian.core.domain

import kotlinx.datetime.LocalDateTime

data class Book (
    val id: String,
    val name: String,
    val currentChapter: String,
    val externalId: String,
    val library: Library,
    val ignoreUntil: LocalDateTime? = null,
)

