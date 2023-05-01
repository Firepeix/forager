package com.tutu.forager.modules.librarian.core.usecase.book

import com.tutu.forager.modules.librarian.core.domain.Book
import com.tutu.forager.modules.librarian.core.exception.InvalidRequestException
import com.tutu.forager.modules.librarian.core.gateway.BookGateway
import com.tutu.forager.util.date.Extend.Companion.toBrazilianTime
import com.tutu.forager.util.result.take
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.LocalDateTime
import me.tatarka.inject.annotations.Inject
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.time.Duration

@Inject
class PostponeBookUseCase(private val gateway: BookGateway) {

    enum class Delay(val prefix: String, val common: String, val duration: Duration) {
        SMALL_DELAY("", "daqui a pouco", Duration.parse("PT5M")),
        FIVE_MINUTES("em ", "5 minutos", Duration.parse("PT5M")),
        TEN_MINUTES("em ", "10 minutos", Duration.parse("PT10M")),
        THIRTY_MINUTES("em ", "30 minutos", Duration.parse("PT30M")),
        ONE_HOUR("em ", "1 hora", Duration.parse("PT1H")),
        ONE_DAY("em ", "1 dia", Duration.parse("P1D")),
        ONE_WEEK("em ", "1 semana", Duration.parse("P7D")),
        ONE_MONTH("em ", "1 mes", Duration.parse("P30D")),
        ONE_YEAR("em ", "1 ano", Duration.parse("P365D"));

        companion object {
            fun parse(value: String?): Result<Delay?> {
                if (value == null) return success(null)
                return values().firstOrNull { "${it.prefix}${it.common}" == value }.take(
                    some = { success(it) },
                    none = { failure(InvalidRequestException("delay")) }
                )
            }
        }
    }

    suspend fun postpone(book: Book, ignoreUntil: LocalDateTime?, delay: Delay?): Result<Unit> {
        val delayDuration = now().plus(delay?.duration ?: Delay.ONE_YEAR.duration)
        val futureDate = ignoreUntil ?: delayDuration.toBrazilianTime()
        return gateway.update(book.copy(ignoreUntil = futureDate))
    }
}