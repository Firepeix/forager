package com.tutu.forager.util.date

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class Extend {
    companion object {
        fun Instant.toBrazilianTime(): LocalDateTime {
            return toLocalDateTime(TimeZone.of("America/Sao_Paulo"))
        }
    }
}