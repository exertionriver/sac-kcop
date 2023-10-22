package swe

import kotlinx.datetime.LocalDateTime
import river.exertion.sac.swe.Julday
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestJulday {

    @Test
    fun testGetJulianUTCTimeDecimal() {
        val testLDT = LocalDateTime(1978,11,16,18,39,0,0)

        println("test localdatetime: $testLDT")

        val uniTimeDec = Julday.getJulianUTCTimeDecimal(testLDT, Julday.UNIVERSAL_TIME)
        println("julian date universal time: $uniTimeDec")
        assertEquals("2443829.2771", "%1.4f".format(uniTimeDec))

        val terTimeDec = Julday.getJulianUTCTimeDecimal(testLDT, Julday.TERRESTRIAL_TIME)
        println("julian date terrestrial time: $terTimeDec")
        assertEquals("2443829.2777", "%1.4f".format(terTimeDec))
    }
}