package swe

import river.exertion.sac.astro.base.Celestial
import river.exertion.sac.astro.base.EarthLocation
import kotlinx.datetime.*
import river.exertion.sac.swe.Julday
import river.exertion.sac.swe.RiseTrans
import org.junit.jupiter.api.Test
import river.exertion.sac.Constants.ALT_TNM
import river.exertion.sac.Constants.LAT_TNM
import river.exertion.sac.Constants.LON_TNM
import river.exertion.sac.Constants.TZ_MST
import river.exertion.sac.Profile
import kotlin.test.assertEquals

@ExperimentalUnsignedTypes
class TestRiseTrans {

    private val refProfile = Profile("testRiseTransProfile"
        , EarthLocation(LON_TNM, LAT_TNM, ALT_TNM, TimeZone.of(TZ_MST)
        , LocalDate(2021, 11,8))
    )

    @Test
    fun testRiseTrans() {

        println(refProfile.earthLocation.getLocalTimeString())

        for (celestial in Celestial.entries) {

//            println(RenderCelestial.fromName(celestial.toString())!!.getLabel())
            print("rising: ") ; pollRising(celestial.ordinal)
            print("setting: ") ; pollSetting(celestial.ordinal)
            print("upper meridian: ") ; pollUpperMeridian(celestial.ordinal)
            print("lower meridian: ") ; pollLowerMeridian(celestial.ordinal)
        }
    }

    private fun pollRising(celestialIdx : Int, earthLocation : EarthLocation = refProfile.earthLocation) {

        val retLDT = RiseTrans.getRiseLDTForCelestial(Julday.getJulianUTCTimeDecimal(earthLocation.utcDateTime), earthLocation, celestialIdx)

        println(EarthLocation.getTimeString(retLDT))
    }

    @Test
    fun testRising() {

        val retLDT = RiseTrans.getRiseLDTForCelestial(
            Julday.getJulianUTCTimeDecimal(refProfile.earthLocation.utcDateTime), refProfile.earthLocation, Celestial.SUN.ordinal)

        assertEquals(2021, retLDT.year)
        assertEquals(11, retLDT.monthNumber)
        assertEquals(8, retLDT.dayOfMonth)
        assertEquals(6, retLDT.hour)
        assertEquals(33, retLDT.minute)
        assertEquals(24, retLDT.second)
//        assertEquals(38029789, retLDT.nanosecond)
    }

    private fun pollSetting(celestialIdx : Int, earthLocation : EarthLocation = refProfile.earthLocation) {

        val retLDT = RiseTrans.getSetLDTForCelestial(Julday.getJulianUTCTimeDecimal(earthLocation.utcDateTime), earthLocation, celestialIdx)

        println(EarthLocation.getTimeString(retLDT))
    }

    @Test
    fun testSetting() {

        val retLDT = RiseTrans.getSetLDTForCelestial(
            Julday.getJulianUTCTimeDecimal(refProfile.earthLocation.utcDateTime), refProfile.earthLocation, Celestial.MERCURY.ordinal)

        assertEquals(2021, retLDT.year)
        assertEquals(11, retLDT.monthNumber)
        assertEquals(8, retLDT.dayOfMonth)
        assertEquals(16, retLDT.hour)
        assertEquals(28, retLDT.minute)
        assertEquals(21, retLDT.second)
//        assertEquals(126128613, retLDT.nanosecond)
    }

    private fun pollUpperMeridian(celestialIdx : Int, earthLocation : EarthLocation = refProfile.earthLocation) {

        val retLDT = RiseTrans.getUpperMeridLDTForCelestial(Julday.getJulianUTCTimeDecimal(earthLocation.utcDateTime), earthLocation, celestialIdx)

        println(EarthLocation.getTimeString(retLDT))
    }

    @Test
    fun testUpperMeridian() {

        val retLDT = RiseTrans.getUpperMeridLDTForCelestial(
            Julday.getJulianUTCTimeDecimal(refProfile.earthLocation.utcDateTime), refProfile.earthLocation, Celestial.MARS.ordinal)

        assertEquals(2021, retLDT.year)
        assertEquals(11, retLDT.monthNumber)
        assertEquals(8, retLDT.dayOfMonth)
        assertEquals(11, retLDT.hour)
        assertEquals(5, retLDT.minute)
        assertEquals(39, retLDT.second)
//        assertEquals(967055559, retLDT.nanosecond)
    }

    private fun pollLowerMeridian(celestialIdx : Int, earthLocation : EarthLocation = refProfile.earthLocation) {

        val retLDT = RiseTrans.getLowerMeridLDTForCelestial(Julday.getJulianUTCTimeDecimal(earthLocation.utcDateTime), earthLocation, celestialIdx)

        println(EarthLocation.getTimeString(retLDT))
    }

    @Test
    fun testLowerMeridian() {

        val retLDT = RiseTrans.getLowerMeridLDTForCelestial(
            Julday.getJulianUTCTimeDecimal(refProfile.earthLocation.utcDateTime), refProfile.earthLocation, Celestial.SATURN.ordinal)

        assertEquals(2021, retLDT.year)
        assertEquals(11, retLDT.monthNumber)
        assertEquals(8, retLDT.dayOfMonth)
        assertEquals(5, retLDT.hour)
        assertEquals(31, retLDT.minute)
        assertEquals(21, retLDT.second)
//        assertEquals(435811936, retLDT.nanosecond)
    }
}
