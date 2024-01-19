package river.exertion.sac.swe

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import swisseph.SweDate
import swisseph.SweDate.SE_GREG_CAL

object Julday {
    const val TERRESTRIAL_TIME = 0
    const val UNIVERSAL_TIME = 1

    const val MIN_IN_HOUR = 60
    const val SEC_IN_HOUR = MIN_IN_HOUR * 60
    const val NSEC_IN_HOUR : Double = SEC_IN_HOUR.toDouble() * 1000000000

    //"The only functions you should ever use for dealing with time/date are swe_julday() and swe_revjul()." - Alois Treindl, 2022-01-31
    //TESTED-BY TestJulday::testGetJulianTimeDecimal()
    fun getJulianTimeDecimal(timeUTC : LocalDateTime, timeFlag : Int = UNIVERSAL_TIME) : Double {

        val hourDec : Double = (timeUTC.hour.toDouble() + timeUTC.minute.toDouble() / MIN_IN_HOUR + timeUTC.second.toDouble() / SEC_IN_HOUR + timeUTC.nanosecond / NSEC_IN_HOUR)

        val sd = SweDate(timeUTC.year, timeUTC.monthNumber, timeUTC.dayOfMonth, hourDec, SE_GREG_CAL)

        val ttmJulDay = sd.julDay + sd.deltaT

        return if (timeFlag == TERRESTRIAL_TIME) ttmJulDay else sd.julDay
    }

    fun getJulianTimeDecimal(timeUTC : Instant, timeFlag : Int = UNIVERSAL_TIME) : Double =
        getJulianTimeDecimal(timeUTC.toLocalDateTime(TimeZone.UTC), timeFlag)
}