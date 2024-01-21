package swe

import org.junit.jupiter.api.Test
import river.exertion.sac.swe.RevJul
import kotlin.test.assertEquals

class TestRevJul {

    @Test
    fun testGetUTC() {

        //atxEarthLocation julday
        var julday = 2456904.006284722
        var retUTC = RevJul.getUTC(julday)

        println("retUTC for julday $julday: $retUTC")
        assertEquals("2014", "%4d".format(retUTC.year))
        assertEquals("09", "%02d".format(retUTC.monthNumber))
        assertEquals("03", "%02d".format(retUTC.dayOfMonth))
        assertEquals("12", "%02d".format(retUTC.hour))
        assertEquals("09", "%02d".format(retUTC.minute))
        assertEquals("02", "%02d".format(retUTC.second))
        assertEquals("999991774", "%04d".format(retUTC.nanosecond))

        //sfeEarthLocation julday
        julday = 2458735.881250000
        retUTC = RevJul.getUTC(julday)

        println("retUTC for julday $julday: $retUTC")
        assertEquals("2019", "%4d".format(retUTC.year))
        assertEquals("09", "%02d".format(retUTC.monthNumber))
        assertEquals("09", "%02d".format(retUTC.dayOfMonth))
        assertEquals("09", "%02d".format(retUTC.hour))
        assertEquals("09", "%02d".format(retUTC.minute))
        assertEquals("00", "%02d".format(retUTC.second))
        assertEquals("8046", "%04d".format(retUTC.nanosecond))

        //tnmEarthLocation julday
        julday = 2459497.257048611
        retUTC = RevJul.getUTC(julday)

        println("retUTC for julday $julday: $retUTC")
        assertEquals("2021", "%4d".format(retUTC.year))
        assertEquals("10", "%02d".format(retUTC.monthNumber))
        assertEquals("09", "%02d".format(retUTC.dayOfMonth))
        assertEquals("18", "%02d".format(retUTC.hour))
        assertEquals("10", "%02d".format(retUTC.minute))
        assertEquals("08", "%02d".format(retUTC.second))
        assertEquals("999995887", "%04d".format(retUTC.nanosecond))
    }
}