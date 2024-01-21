package river.exertion.sac.swe

import kotlinx.datetime.*
import swisseph.SweDate
import swisseph.SweDate.SE_GREG_CAL

object RevJul {

    //"The only functions you should ever use for dealing with time/date are swe_julday() and swe_revjul()." - Alois Treindl, 2022-01-31
    //TESTED-BY TestRevJul::testGetUTC()
    fun getUTC(julday : Double) : LocalDateTime {

        val sd = SweDate(julday, SE_GREG_CAL)

        val retHour = sd.hour.toInt()
        val retHourRemainder = sd.hour - retHour

        val retMin = (retHourRemainder * Julday.MIN_IN_HOUR).toInt()
        val retMinRemainder = (retMin.toDouble() / Julday.MIN_IN_HOUR)

        val retSec = ((retHourRemainder - retMinRemainder) * Julday.SEC_IN_HOUR).toInt()
        val retSecRemainder = (retSec.toDouble() / Julday.SEC_IN_HOUR)

        val retNSec = ((retHourRemainder - retMinRemainder - retSecRemainder) * Julday.NSEC_IN_HOUR).toInt()

        return LocalDateTime(sd.year, sd.month, sd.day, retHour, retMin, retSec, retNSec)//.toInstant(TimeZone.UTC).toLocalDateTime(timeZone)
    }
}