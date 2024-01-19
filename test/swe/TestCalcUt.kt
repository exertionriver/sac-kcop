package swe

import TestEarthLocations
import river.exertion.sac.astro.base.Celestial
import river.exertion.sac.swe.CalcUt
import river.exertion.sac.swe.Houses
import river.exertion.sac.swe.Julday
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestCalcUt {

    /*slight celestial variations from online swetest, e.g. swetest shows:
        Sun : 166.4102357
        Moon : 293.8492244
        Mercury : 171.2312683
    getCelestialsData() shows:
        SUN, 166.41025540926918
        MOON, 293.8494684770537
        MERCURY, 171.2313052937299
    for same Julday. Perhaps diff between UT1 and UTC?
    Houses tie out ok with online swetest using UTC.
    */
    @Test
    fun testGetCelestialsData() {
        val testUTC = TestEarthLocations.sfeEarthLocation.utcDateTime
        val testLat = TestEarthLocations.sfeEarthLocation.latitude
        val testLong = TestEarthLocations.sfeEarthLocation.longitude

        println("test localdatetime: $testUTC")
        println("test lat/long: $testLat / $testLong")

        val uniTimeDec = Julday.getJulianTimeDecimal(testUTC)
        val uniTimeHouses = Houses.getCelestialHousesData(uniTimeDec, testLat, testLong)
        val uniCelestials = CalcUt.getCelestialsData(uniTimeDec, uniTimeHouses)

        println ("Julday: $uniTimeDec")

        println("Universal Time Celestials (raw-long)")
        uniCelestials.forEachIndexed { idx, celestial -> println ("${Celestial.fromOrdinal(idx)}, ${celestial.longitude}") }
        assertEquals("166.4103", "%1.4f".format(uniCelestials[0].longitude))
        assertEquals("164.1089", "%1.4f".format(uniCelestials[4].longitude))
        assertEquals("347.3360", "%1.4f".format(uniCelestials[8].longitude))

        val uniCelestialsDeg = uniCelestials.map{ it.longitude.toInt() }
        val uniCelestialsMin = uniCelestials.mapIndexed { idx, celestial -> ((celestial.longitude - uniCelestialsDeg[idx]) * 60).toInt() }
        val uniCelestialsSec = uniCelestials.mapIndexed { idx, celestial -> ((celestial.longitude - uniCelestialsDeg[idx] - uniCelestialsMin[idx].toDouble() / 60) * 3600) }

        println("Universal Time Celestials (calc'd-long)")
        uniCelestialsDeg.forEachIndexed { idx, celestialDeg -> println ("${Celestial.fromOrdinal(idx)}, $celestialDeg ${uniCelestialsMin[idx]}'${uniCelestialsSec[idx]}") }
        assertEquals(293, uniCelestialsDeg[1])
        assertEquals(255, uniCelestialsDeg[5])
        assertEquals(290, uniCelestialsDeg[9])

        assertEquals(13, uniCelestialsMin[2])
        assertEquals(58, uniCelestialsMin[6])
        assertEquals(58, uniCelestialsMin[10])

        assertEquals("25.1779", "%1.4f".format(uniCelestialsSec[3]))
        assertEquals("42.5052", "%1.4f".format(uniCelestialsSec[7]))
        assertEquals("16.8798", "%1.4f".format(uniCelestialsSec[11]))
    }
}