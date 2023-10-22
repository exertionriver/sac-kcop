package river.exertion.sac.analysis

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import river.exertion.sac.astro.value.Value

@Serializable
data class AstroDataRow(val pollUTC : LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
                        , val value : Value = Value())