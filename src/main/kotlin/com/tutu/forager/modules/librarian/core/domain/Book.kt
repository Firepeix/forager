package com.tutu.forager.modules.librarian.core.domain

import kotlinx.datetime.LocalDateTime

typealias Chapter = String

data class Book (
    val id: String? = null,
    val name: String,
    val currentChapter: Chapter,
    val externalId: String,
    val library: Library,
    val ignoreUntil: LocalDateTime? = null,
)

