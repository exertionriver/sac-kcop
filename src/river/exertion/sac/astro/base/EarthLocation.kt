package river.exertion.sac.astro.base

import kotlinx.datetime.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import river.exertion.sac.console.state.EntryState
import river.exertion.sac.Constants.ALT_TNM
import river.exertion.sac.Constants.LAT_TNM
import river.exertion.sac.Constants.LON_TNM
import river.exertion.sac.Constants.TZ_MST

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class EarthLocation(var longitude : Double = 0.0
    , var latitude : Double = 0.0
    , var altitude : Int = 0
    , var timeZone : TimeZone = TimeZone.currentSystemDefault()
    , var utcDateTime : LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    , val timeUnknown : Boolean = false ) {

    constructor(initLongitude : Double, initLatitude : Double, initAltitude : Int, initTimezone : TimeZone, initUtcDate : LocalDate) :
        this(initLongitude, initLatitude, initAltitude, initTimezone, getDefaultLocalDateTime(initUtcDate), true)

    constructor(longitude: String, latitude: String, altitude : String, timeZone: String, utcDateTime: String) :
            this(longitude.toDouble(), latitude.toDouble(), altitude.toInt(), TimeZone.of(timeZone), utcDateTime.toLocalDateTime())

    @Transient
    var localDateTime = utcDateTime.toInstant(TimeZone.UTC).toLocalDateTime(timeZone)

    fun recalc() {
        localDateTime = utcDateTime.toInstant(TimeZone.UTC).toLocalDateTime(timeZone)
    }

    fun getUTCDateTimeString() = getDateTimeString(utcDateTime)

    fun getUTCTimeString() = getTimeString(utcDateTime)

    fun getLocalTimeString() = getTimeString(localDateTime)

    fun getTimezoneOffsetString() : String = getOffsetStringDouble(this.timeZone)

    fun getTimezoneOffsetInt() : Int = getTimezoneOffsetInt(this.timeZone)

    override fun toString(): String {
        return "EarthLocation(longitude=$longitude, latitude=$latitude, altitude=$altitude, timeZone=$timeZone, utcDateTime=$utcDateTime, localDateTime=$localDateTime, timeUnknown=$timeUnknown)"
    }

    companion object {
        fun getDateTimeString(dateTime : LocalDateTime) : String {

            return dateTime.year.toString() + EntryState.DATE_ENTRY.getDelim() + dateTime.monthNumber.toString().padStart(2, '0') + EntryState.DATE_ENTRY.getDelim() + dateTime.dayOfMonth.toString().padStart(2, '0') + "@" +
                    dateTime.hour.toString().padStart(2, '0') + EntryState.TIME_ENTRY.getDelim() + dateTime.minute.toString().padStart(2, '0') + EntryState.TIME_ENTRY.getDelim() + dateTime.second.toString().padStart(2, '0') + "." + dateTime.nanosecond.toString()
        }

        fun getDateString(dateTime : LocalDateTime) : String {

            return dateTime.year.toString() + EntryState.DATE_ENTRY.getDelim() + dateTime.monthNumber.toString().padStart(2, '0') + EntryState.DATE_ENTRY.getDelim() + dateTime.dayOfMonth.toString().padStart(2, '0')
        }

        fun getTimeString(dateTime : LocalDateTime) : String {

            return dateTime.hour.toString().padStart(2, '0') + EntryState.TIME_ENTRY.getDelim() + dateTime.minute.toString().padStart(2, '0') + EntryState.TIME_ENTRY.getDelim() + dateTime.second.toString().padStart(2, '0')
        }

        fun getDefaultEarthLocation(utcDate : LocalDate) = EarthLocation(LON_TNM, LAT_TNM, ALT_TNM, TimeZone.of(TZ_MST), utcDate)

        fun getDefaultLocalDateTime(ldt : LocalDate) : LocalDateTime = LocalDateTime(ldt.year, ldt.monthNumber, ldt.dayOfMonth, 12, 0,0)

        fun getOffsetStringInt(timeZone: TimeZone) : String = getTimeZoneOffsetStringInt(getTimezoneOffsetInt(timeZone))

        fun getTimeZoneOffsetStringInt(timeZoneOffset : Int) : String = if (timeZoneOffset >= 0) "+${timeZoneOffset}" else "$timeZoneOffset"

        private fun getTimezoneOffsetInt(timeZone : TimeZone) : Int =
            if (timeZone == TimeZone.of("UTC")) 0
            else timeZone.offsetAt(Clock.System.now()).toString().split(":")[0].toInt()

      //  fun getTimeZoneFromOffsetInt(timeZoneOffset : Int) : TimeZone = TimeZone.of(getTimeZoneString(timeZoneOffset))

    //    fun getTimeZoneString(timeZoneOffset : Int) : String = if (timeZoneOffset == 0) "Z" else getTimeZoneOffsetStringInt(timeZoneOffset)

        fun getOffsetStringDouble(timeZone: TimeZone) : String = getTimeZoneOffsetStringDouble(getTimezoneOffsetInt(timeZone))

        private fun getTimeZoneOffsetStringDouble(timeZoneOffset : Int) : String = when {
            (timeZoneOffset > 0) -> "UTC+${timeZoneOffset.toDouble()}"
            (timeZoneOffset < 0) -> "UTC${timeZoneOffset.toDouble()}"
            else -> "UTC"
        }
    }
}