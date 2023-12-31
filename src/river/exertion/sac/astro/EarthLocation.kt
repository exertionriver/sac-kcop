package river.exertion.sac.astro

import kotlinx.datetime.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import river.exertion.kcop.profile.Profile
import river.exertion.sac.Constants
import river.exertion.sac.console.state.EntryState

//https://www.iana.org/time-zones
@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class EarthLocation(
    var tag : String = Profile.genName()
    , var longitude : Double = 0.0
    , var latitude : Double = 0.0
    , var altitude : Int = 0
    , var timeZone : TimeZone = TimeZone.currentSystemDefault()
    , var utcDateTime : LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    , val timeUnknown : Boolean = false ) {

    constructor(initLongitude : Double, initLatitude : Double, initAltitude : Int, initTimezone : TimeZone, initUtcDate : LocalDate) :
        this(longitude = initLongitude, latitude = initLatitude, altitude = initAltitude, timeZone = initTimezone, utcDateTime = getDefaultLocalDateTime(initUtcDate), timeUnknown = true)

    public constructor(tag: String, longitude: String, latitude: String, altitude : String, timeZone: String, utcDateTime: String) :
            this(tag, longitude.toDouble(), latitude.toDouble(), altitude.toInt(), TimeZone.of(timeZone), utcDateTime.toLocalDateTime())

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

        fun getDefaultEarthLocation(utcDate : LocalDate) = EarthLocation().apply { this.utcDateTime = utcDate.atTime(Clock.System.now().toLocalDateTime(
            TimeZone.UTC).time) }

        fun getDefaultLocalDateTime(ldt : LocalDate) : LocalDateTime = LocalDateTime(ldt.year, ldt.monthNumber, ldt.dayOfMonth, 12, 0,0)

        fun getOffsetStringInt(timeZone: TimeZone) : String = getTimeZoneOffsetStringInt(getTimezoneOffsetInt(timeZone))

        fun getTimeZoneOffsetStringInt(timeZoneOffset : Int) : String = if (timeZoneOffset >= 0) "+${timeZoneOffset}" else "$timeZoneOffset"

        private fun getTimezoneOffsetInt(timeZone : TimeZone) : Int =
            if (timeZone == TimeZone.of("UTC")) 0
            else timeZone.offsetAt(Clock.System.now()).toString().split(":")[0].toInt()

        fun getTimeZoneFromOffsetInt(timeZoneOffset : Int) : TimeZone = TimeZone.of(getTimeZoneString(timeZoneOffset))

        fun getTimeZoneString(timeZoneOffset : Int) : String = if (timeZoneOffset == 0) "Z" else getTimeZoneOffsetStringInt(timeZoneOffset)

        fun getOffsetStringDouble(timeZone: TimeZone) : String = getTimeZoneOffsetStringDouble(getTimezoneOffsetInt(timeZone))

        private fun getTimeZoneOffsetStringDouble(timeZoneOffset : Int) : String = when {
            (timeZoneOffset > 0) -> "UTC+${timeZoneOffset.toDouble()}"
            (timeZoneOffset < 0) -> "UTC${timeZoneOffset.toDouble()}"
            else -> "UTC"
        }

        fun getEarthLongitudeLabel() = Constants.SYM_EARTH_LONG + "(" + Constants.SYM_EARTH + ")"
        fun getUTCLongitudeLabel() = Constants.SYM_EARTH_LONG + "(" + Constants.SYM_UTC + ")"
        fun getEarthLatitudeLabel() = Constants.SYM_EARTH_LAT + "(" + Constants.SYM_EARTH + ")"
        fun getEarthAltitudeLabel() = Constants.SYM_EARTH_ALT + "(" + Constants.SYM_EARTH + ")"
        fun getEarthTimezoneLabel() = Constants.SYM_TZ + "(" + Constants.SYM_EARTH + ")" //used for manually-input timezone
        fun getEarthUTCTimeLabel() = Constants.SYM_EMPTY_HGLASS + "(" + Constants.SYM_UTC + ")"
        fun getEarthUTCDateLabel() = Constants.SYM_GREG_CALENDAR + "(" + Constants.SYM_UTC + ")"
        fun getEarthLocalTimeLabel() = Constants.SYM_EMPTY_HGLASS + "(" + Constants.SYM_TZ + ")"
        fun getEarthLocalDateLabel() = Constants.SYM_GREG_CALENDAR + "(" + Constants.SYM_TZ + ")"

        fun getRenderTimeLabel(dateTime : LocalDateTime, dateTimeLabel : String) = dateTimeLabel + ": " +
                dateTime.hour.toString().padStart(2, '0') + EntryState.TIME_ENTRY.getDelim() +
                dateTime.minute.toString().padStart(2, '0') + EntryState.TIME_ENTRY.getDelim() +
                dateTime.second.toString().padStart(2, '0')

        fun getRenderLocalTimeLabel(localDateTime : LocalDateTime) = getRenderTimeLabel(localDateTime, getEarthLocalTimeLabel())

        fun getRenderUTCTimeLabel(utcDateTime : LocalDateTime) = getRenderTimeLabel(utcDateTime, getEarthUTCTimeLabel())

        fun getRenderDateLabel(dateTime : LocalDateTime, dateTimeLabel : String) = dateTimeLabel + ": " +
                dateTime.year.toString().padStart(4, '0') + EntryState.DATE_ENTRY.getDelim() +
                dateTime.monthNumber.toString().padStart(2, '0') + EntryState.DATE_ENTRY.getDelim() +
                dateTime.dayOfMonth.toString().padStart(2, '0')

        fun getRenderLocalDateLabel(localDateTime : LocalDateTime) = getRenderDateLabel(localDateTime, getEarthLocalDateLabel())

        fun getRenderUTCDateLabel(utcDateTime : LocalDateTime) = getRenderDateLabel(utcDateTime, getEarthUTCDateLabel())

        fun getRenderLocalTimezoneLabel(timeZoneOffsetString : String) = getEarthTimezoneLabel() + ": " +
                Constants.SYM_UTC + timeZoneOffsetString

        fun getRenderUTCTimezoneLabel() = getUTCLongitudeLabel() + ":" + "0.0".padStart(7, ' ')

        fun getRenderLocalLatitudeLabel(latitude : Double) = getEarthLatitudeLabel() + "[-S]:" +
                latitude.toString().padStart(10, ' ')

        fun getRenderLocalLongitudeLabel(longitude : Double) = getEarthLongitudeLabel() + "[-W]:" +
                longitude.toString().padStart(10, ' ')


        fun getRenderEarthLocationDataFirstLine(earthLocation: EarthLocation) : String {

            val timeZoneOffsetString = earthLocation.getTimezoneOffsetString()
            val postTzPad = 10 - timeZoneOffsetString.length // "+3.0" == 6, "-3.0" == 6, "-12.0" == 5

            return " " + getRenderLocalTimeLabel(earthLocation.localDateTime) + " ".padStart(6, ' ') +
                    getRenderLocalDateLabel(earthLocation.localDateTime) + " ".padStart(6, ' ') +
                    getRenderLocalTimezoneLabel(earthLocation.getTimezoneOffsetString()) + " ".padStart(postTzPad, ' ') +
                    getRenderLocalLatitudeLabel(earthLocation.latitude)
        }

        fun getRenderEarthLocationDataSecondLine(earthLocation: EarthLocation) : String {

            return " " + getRenderUTCTimeLabel(earthLocation.utcDateTime) + " ".padStart(6, ' ') +
                    getRenderUTCDateLabel(earthLocation.utcDateTime) + " ".padStart(7, ' ') +
                    getRenderUTCTimezoneLabel() + " ".padStart(6, ' ') +
                    getRenderLocalLongitudeLabel(earthLocation.longitude)
        }
    }
}