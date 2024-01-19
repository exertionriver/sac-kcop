package river.exertion.sac.swe

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import swisseph.SweDate
import swisseph.SweDate.SE_GREG_CAL

object RevJul {

    fun getLDT(julTime : Double, timeZone : TimeZone = TimeZone.UTC) : LocalDateTime {

        val sd = SweDate(julTime, SE_GREG_CAL)

        val retHour = sd.hour.toInt()
        val retHourRemainder = sd.hour - retHour

        val retMin = (retHourRemainder * Julday.MIN_IN_HOUR).toInt()
        val retMinRemainder = (retMin.toDouble() / Julday.MIN_IN_HOUR)

        val retSec = ((retHourRemainder - retMinRemainder) * Julday.SEC_IN_HOUR).toInt()
        val retSecRemainder = (retSec.toDouble() / Julday.SEC_IN_HOUR)

        val retNSec = ((retHourRemainder - retMinRemainder - retSecRemainder) * Julday.NSEC_IN_HOUR).toInt()

        return LocalDateTime(sd.year, sd.month, sd.day, retHour, retMin, retSec, retNSec).toInstant(TimeZone.UTC).toLocalDateTime(timeZone)
    }

    fun getInstant(julTime : Double, timeZone : TimeZone = TimeZone.UTC) = getLDT(julTime, timeZone).toInstant(timeZone)
}