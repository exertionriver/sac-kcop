package astro.base

import TestEarthLocations
import kotlinx.datetime.LocalDateTime
import river.exertion.sac.astro.base.Celestial
import river.exertion.sac.astro.CelestialData
import river.exertion.sac.astro.CelestialData.Companion.ROUND_PRECISION_SEC_DEC
import river.exertion.sac.swe.CalcUt
import river.exertion.sac.swe.Houses
import river.exertion.sac.swe.Julday
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestCelestialData {

    @Test
    fun testGetFormattedSignLongitude() {
        println ("test earthlocation:" + TestEarthLocations.atxEarthLocation)

        val uniTimeDec = Julday.getJulianUTCTimeDecimal(TestEarthLocations.atxEarthLocation.utcDateTime, Julday.UNIVERSAL_TIME)
        val uniTimeHouses = Houses.getCelestialHousesData(uniTimeDec, TestEarthLocations.atxEarthLocation.latitude, TestEarthLocations.atxEarthLocation.longitude )
        val uniCelestials = CalcUt.getCelestialsData(uniTimeDec, uniTimeHouses)

        println("Universal Time Celestials (raw-long)")
        uniCelestials.forEachIndexed { idx, celestial -> println ("${Celestial.fromOrdinal(idx)}, ${celestial.longitude}") }
        assertEquals("234.0540", "%1.4f".format(uniCelestials[0].longitude))
        assertEquals("250.6743", "%1.4f".format(uniCelestials[4].longitude))
        assertEquals("257.1584", "%1.4f".format(uniCelestials[8].longitude))

        val uniCelestialsFormattedSecDec = uniCelestials.map{ CelestialData.getFormattedSignLongitude(it.longitude, ROUND_PRECISION_SEC_DEC) }

        println("Universal Time Celestials (calc'd-long, sign, second-decimal precision)")
        uniCelestialsFormattedSecDec.forEachIndexed { idx, celestialFormatted -> println ("${Celestial.fromOrdinal(idx)}, $celestialFormatted") }
        assertEquals("16°48'20.4694", uniCelestialsFormattedSecDec[1]) //swetest varies with "16 48'20.4697
        assertEquals(" 8°55'26.3546", uniCelestialsFormattedSecDec[5])
        assertEquals("18° 0'30.5948", uniCelestialsFormattedSecDec[9])

        val uniCelestialsFormattedSec = uniCelestials.map{ CelestialData.getFormattedSignLongitude(it.longitude) }

        println("Universal Time Celestials (calc'd-long, sign, second precision (default))")
        uniCelestialsFormattedSec.forEachIndexed { idx, celestialFormatted -> println ("${Celestial.fromOrdinal(idx)}, $celestialFormatted") }
        assertEquals("16°28'25", uniCelestialsFormattedSec[2])
        assertEquals("12°38'11", uniCelestialsFormattedSec[6])
        assertEquals("24°55'19", uniCelestialsFormattedSec[10])
    }

}