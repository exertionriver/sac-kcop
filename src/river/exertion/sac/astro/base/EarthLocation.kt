package river.exertion.sac.astro.base

import kotlinx.datetime.*
import river.exertion.sac.astro.state.EntryState
import river.exertion.sac.Constants.ALT_TNM
import river.exertion.sac.Constants.LAT_TNM
import river.exertion.sac.Constants.LON_TNM
import river.exertion.sac.Constants.TZ_MST

@ExperimentalUnsignedTypes
data class EarthLocation(var longitude : Double = LON_TNM
    , var latitude : Double = LAT_TNM
    , var altitude : Double = ALT_TNM
    , var timeZone : TimeZone = TimeZone.currentSystemDefault()
    , var utcDateTime : LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    , val timeUnknown : Boolean = false ) {

    constructor(initLongitude : Double, initLatitude : Double, initAltitude : Double, initTimezone : TimeZone, initUtcDate : LocalDate) :
        this(initLongitude, initLatitude, initAltitude, initTimezone, getDefaultLocalDateTime(initUtcDate), true)

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
            if (timeZone == TimeZone.UTC) 0 else timeZone.offsetAt(Clock.System.now()).toString().split(":")[0].toInt()


        fun getTimeZoneFromOffsetInt(timeZoneOffset : Int) : TimeZone = TimeZone.of(getTimeZoneString(timeZoneOffset))

        fun getTimeZoneString(timeZoneOffset : Int) : String = if (timeZoneOffset == 0) "Z" else getTimeZoneOffsetStringInt(timeZoneOffset)


        fun getOffsetStringDouble(timeZone: TimeZone) : String = getTimeZoneOffsetStringDouble(getTimezoneOffsetInt(timeZone))

        private fun getTimeZoneOffsetStringDouble(timeZoneOffset : Int) : String = if (timeZoneOffset >= 0) "+${timeZoneOffset.toDouble()}" else "${timeZoneOffset.toDouble()}"
    }
}